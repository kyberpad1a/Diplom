package com.example.diplom.view.user;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelOrder;
import com.example.diplom.model.modelOrderGood;
import com.example.diplom.repo.OrderGoodRepository;
import com.example.diplom.repo.OrderRepository;
import com.example.diplom.repo.UserRepository;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Класс для отображения истории заказов пользователей.
 * Является потомком класса VerticalLayout.
 */
@PageTitle("История заказов")
@Route(value = "/ordershistory", layout = userPage.class)
public class OrderHistory extends VerticalLayout {

    /**
     * Репозиторий для доступа к заказам.
     */
    @Autowired
    OrderRepository orderRepository;

    /**
     * Репозиторий для доступа к пользователям.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Идентификатор выбранного заказа.
     */
    Long idOrder;

    /**
     * Идентификатор пользователя.
     */
    Long id;

    /**
     * Таблица для отображения заказов.
     */
    Grid<modelOrder> grid = new Grid<>(modelOrder.class, false);

    /**
     * Создает экземпляр класса.
     *
     * @param orderRepository репозиторий для доступа к заказам
     * @param userRepository репозиторий для доступа к пользователям
     */
    public OrderHistory(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        layout.add(grid);
        add(layout);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        id = userRepository.findByUsername(username).getIDUser();
        grid.addColumn(modelOrder::getID_Order).setHeader("Код заказа").setWidth("50%").setSortable(true);
        grid.addColumn(modelOrder::getOrder_Date).setHeader("Дата заказа").setWidth("50%").setSortable(true);
        grid.setItems(orderRepository.findAllByPaymentStatusIsTrueAndUser_IDUser(id));


    }

}
