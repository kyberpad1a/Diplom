package com.example.diplom.view.user;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelOrderGood;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.OrderGoodRepository;
import com.example.diplom.repo.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

@PageTitle("Корзина")
@Route(value = "/cart", layout = userPage.class)
public class shoppingCart extends VerticalLayout {
    Grid<modelOrderGood> grid = new Grid<>(modelOrderGood.class, false);
    final
    UserRepository userRepository;
    final
    OrderGoodRepository orderGoodRepository;
    Long id;
    public shoppingCart(UserRepository userRepository, OrderGoodRepository orderGoodRepository){
        grid.addColumn(item -> item.getGoods().getGood_Name()).setHeader("Товар").setWidth("65%");
        grid.addColumn(modelOrderGood::getGoodQuantity).setHeader("Количество");
        Button btnConfirm=new Button("Оформить заказ");
        grid.addComponentColumn(item->{
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));

            HorizontalLayout layout = new HorizontalLayout();
            layout.add(btnDelete);
            btnDelete.addClickListener(buttonClickEvent -> {
                orderGoodRepository.delete(item);
                gridRefresh(orderGoodRepository, userRepository);
            });
                    return layout;
                }).setTextAlign(ColumnTextAlign.START).setFrozenToEnd(true).setFooter(btnConfirm);
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
}
