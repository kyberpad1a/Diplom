package com.example.diplom.view.user;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelOrderGood;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.OrderGoodRepository;
import com.example.diplom.repo.UserRepository;
import com.example.diplom.view.DeniedAccessView;
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

@PageTitle("Корзина")
@Route(value = "/cart", layout = userPage.class)

public class shoppingCart extends VerticalLayout implements BeforeEnterObserver {
    Grid<modelOrderGood> grid = new Grid<>(modelOrderGood.class, false);
    @Autowired
    final
    UserRepository userRepository;
    final
    OrderGoodRepository orderGoodRepository;
    Long id;
    @Autowired
    private HttpServletRequest req;


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
//        grid.addColumn(modelOrderGood::getGoodQuantity).setHeader("Количество");
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
    }

    private void gridRefresh(OrderGoodRepository repository, UserRepository userRepository)

    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        id = userRepository.findByUsername(username).getIDUser();
        try{
            if (repository.findAllByOrder_PaymentStatusAndOrder_User_IDUser(false, id)!=null)
            {grid.setItems(repository.findAllByOrder_PaymentStatusAndOrder_User_IDUser(false, id));}
        }
        catch (NullPointerException ex){
            Notification.show("Корзина пуста", 10000, Notification.Position.BOTTOM_CENTER);
            grid.setItems(Collections.emptyList());
        }

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                    authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("USER"))) {
                beforeEnterEvent.rerouteTo(DeniedAccessView.class);
            }
        }
    }

