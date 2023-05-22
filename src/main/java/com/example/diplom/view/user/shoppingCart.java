package com.example.diplom.view.user;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelOrderGood;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.OrderGoodRepository;
import com.example.diplom.repo.UserRepository;
import com.example.diplom.view.DeniedAccessView;
import com.example.diplom.view.auth.loginPage;
import com.example.diplom.view.auth.registrationPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Класс для отображения корзины покупок пользователя.
 * Реализует интерфейс BeforeEnterObserver для проверки прав доступа к странице.
 * Наследуется от VerticalLayout для вертикального размещения элементов на странице.
 */
public class shoppingCart extends VerticalLayout implements BeforeEnterObserver {

    /**
     * Таблица для отображения товаров в корзине.
     */
    Grid<modelOrderGood> grid = new Grid<>(modelOrderGood.class, false);

    /**
     * Репозиторий пользователей.
     */
    @Autowired
    final UserRepository userRepository;

    /**
     * Репозиторий заказа товаров.
     */
    final OrderGoodRepository orderGoodRepository;

    /**
     * Идентификатор пользователя.
     */
    Long id;

    /**
     * Объект, представляющий HTTP-запрос.
     */
    @Autowired
    private HttpServletRequest req;

    /**
     * Конструктор класса.
     * Принимает репозитории пользователей и заказа товаров.
     * Создает на странице таблицу для отображения товаров в корзине.
     * Создает элементы управления (кнопки и поля) для каждой записи в таблице.
     * При изменении количества товаров или удалении товара из корзины сохраняет изменение/удаляет запись в БД.
     * Проверяет наличие товаров в корзине и при их отсутствии выводит соответствующее сообщение.
     * При нажатии на кнопку "Оформить заказ" перенаправляет пользователя на страницу создания заказа.
     */
    public shoppingCart(UserRepository userRepository, OrderGoodRepository orderGoodRepository){

        Label total = new Label();
        Collection<modelOrderGood> modelOrderGood = orderGoodRepository.findAllByOrder_PaymentStatusAndOrder_User_IDUser(false, id);
        double price = 0;
        for (modelOrderGood item:modelOrderGood) {

            price+=item.getGoods().getGood_Price();
            total.setText(String.valueOf(price));
        }

        grid.addColumn(item -> item.getGoods().getGood_Name()).setHeader("Товар").setWidth("55%").setFooter(total);
        grid.addColumn(item -> item.getGoods().getGood_Price()).setHeader("Цена");
        Button btnConfirm=new Button("Оформить заказ");
        grid.addComponentColumn(item->{


            HorizontalLayout layout = new HorizontalLayout();
            IntegerField field = new IntegerField();
            field.setValue(item.getGoodQuantity());
            field.setMin(1);
            layout.add(field);
            field.setStep(1);
            field.setStepButtonsVisible(true);
            field.addValueChangeListener(integerFieldIntegerComponentValueChangeEvent -> {
               item.setGoodQuantity(integerFieldIntegerComponentValueChangeEvent.getValue());
               item.setOrder(item.getOrder());
               item.setGoods(item.getGoods());
               orderGoodRepository.save(item);
            });
            return layout;
        }).setHeader("Количество").setTextAlign(ColumnTextAlign.START).setFooter(btnConfirm).setWidth("15%");
        grid.addComponentColumn(item->{
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));

            HorizontalLayout layout = new HorizontalLayout();
            layout.add(btnDelete);
            btnDelete.addClickListener(buttonClickEvent -> {
                orderGoodRepository.delete(item);
                gridRefresh(orderGoodRepository, userRepository);
            });
                    return layout;
                });
        add(grid);
        gridRefresh(orderGoodRepository, userRepository);


        this.userRepository = userRepository;
        this.orderGoodRepository = orderGoodRepository;
        btnConfirm.addClickListener(buttonClickEvent -> {
            if (grid.getDataProvider().size(new Query<>()) != 0)
            UI.getCurrent().navigate(OrderPage.class);
            else Notification.show("Корзина пуста", 3000, Notification.Position.BOTTOM_CENTER);
        });
    }

    private void gridRefresh(OrderGoodRepository repository, UserRepository userRepository)

    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{id = userRepository.findByUsername(username).getIDUser();}
        catch (NullPointerException ex){
            UI.getCurrent().navigate(loginPage.class);
        }

        try{
            if (repository.findAllByOrder_PaymentStatusAndOrder_User_IDUser(false, id)!=null)
            {grid.setItems(repository.findAllByOrder_PaymentStatusAndOrder_User_IDUser(false, id));}
        }
        catch (NullPointerException ex){
            Notification.show("Корзина пуста", 10000, Notification.Position.BOTTOM_CENTER);
            grid.setItems(Collections.emptyList());
        }

    }
    /**
     * Обрабатывает событие перед входом в представление.
     * @param beforeEnterEvent Событие перед входом в представление.
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("USER"))) {
                beforeEnterEvent.rerouteTo(loginPage.class);
            }
        }
    }

