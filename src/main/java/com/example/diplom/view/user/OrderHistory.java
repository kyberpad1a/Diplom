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

@PageTitle("История заказов")
@Route(value = "/ordershistory", layout = userPage.class)
public class OrderHistory extends VerticalLayout {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    Long idOrder;
    Long id;
    Grid<modelOrder> grid = new Grid<>(modelOrder.class, false);
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
        //grid.setItemDetailsRenderer(createGoodDetailsRenderer());


    }

//    public static ComponentRenderer<GoodDetailsFormLayout, modelOrder> createGoodDetailsRenderer() {
//        return new ComponentRenderer<>(
//                GoodDetailsFormLayout::new,
//                GoodDetailsFormLayout::setGood);
//    }
//    public static class GoodDetailsFormLayout extends FormLayout {
//        TextArea addGoodName = new TextArea();
//        IntegerField addQuantity = new IntegerField();
//
//
//
//
//        public GoodDetailsFormLayout() {
//
//
//            Stream.of(addGoodName, addQuantity).forEach(field -> {
//                field.setReadOnly(true);
//                add(field);
//
//            });
//
//        }
//
//        public void setGood(OrderGoodRepository repository) {
//            Collection<modelOrderGood> collection = repository.findAllByOrder_ID_Order();
//            for (modelOrderGood good1:collection)
//            {addGoodName.setValue(good1.getGoods().getGood_Name());
//            addQuantity.setValue(good1.getGoodQuantity());
//            }
//        }
//    }
}
