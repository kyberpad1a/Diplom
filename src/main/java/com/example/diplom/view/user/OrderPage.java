package com.example.diplom.view.user;

import com.example.diplom.api.AddressModel;
import com.example.diplom.api.AsyncRestClientService;

import com.example.diplom.model.ModelShipping;
import com.example.diplom.model.modelOrder;
import com.example.diplom.model.modelUser;
import com.example.diplom.repo.OrderRepository;
import com.example.diplom.repo.ShippingRepository;
import com.example.diplom.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@PageTitle("Оформить заказ")
@Route(value = "/order", layout = userPage.class)
public class OrderPage extends VerticalLayout {
    @Autowired
    private UserRepository userRepository;

    private Binder<ModelShipping> binder = new BeanValidationBinder<>(ModelShipping.class);

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;
    boolean pressFlag = false;
    Long id;
    AsyncRestClientService clientService = new AsyncRestClientService();
    public OrderPage(UserRepository userRepository, OrderRepository orderRepository, ShippingRepository shippingRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.shippingRepository = shippingRepository;
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout layout1 = new HorizontalLayout();
        H2 label = new H2("Оформление доставки");
        layout1.setAlignSelf(Alignment.STRETCH);
        VerticalLayout layout2 = new VerticalLayout();
        TextField addressText = new TextField("Введите адрес");
        addressText.setWidth("60%");
        H4 notice = new H4("Уведомляем Вас, что в текущий момент оплата заказа производится при получении");
        IntegerField apNumber = new IntegerField("Введите номер квартиры");
        apNumber.setWidth("20%");
        layout1.setWidthFull();
        layout1.setAlignItems(Alignment.STRETCH);
        Button btnConfirm = new Button("Подтвердить заказ");
        Button btnRecieve = new Button("Получить");
        btnRecieve.addClickListener(buttonClickEvent -> {
            String value = addressText.getValue();
            //clientService.getAllComments();
            try {
                addressText.setValue(clientService.getAllCommentsAsync(value));
                pressFlag = true;
                btnConfirm.setEnabled(pressFlag);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });
        layout1.add(addressText, apNumber);
        layout2.add(layout1, btnRecieve);
        layout2.setWidthFull();
        layout2.setAlignSelf(Alignment.STRETCH);
        layout.setAlignSelf(Alignment.STRETCH);
        layout.setSizeFull();
        layout.add(label, layout2, btnRecieve, notice);
        add(layout, btnConfirm);
        btnConfirm.setEnabled(false);

        binder.forField(addressText).asRequired("Заполните поле 'Адрес'").bind(ModelShipping::getShippingAddress, ModelShipping::setShippingAddress);
        binder.forField(apNumber).asRequired("Заполните поле 'Квартира'").bind(ModelShipping::getShippingApartment, ModelShipping::setShippingApartment);
        binder.addStatusChangeListener(e -> btnConfirm.setEnabled(binder.isValid()));
        btnConfirm.addClickListener(buttonClickEvent -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            id = userRepository.findByUsername(username).getIDUser();
            ModelShipping modelShipping = new ModelShipping();
            modelShipping.setOrder(orderRepository.findByPaymentStatusIsFalseAndUser_IDUser(id));
            modelShipping.setShippingAddress(addressText.getValue());
            System.out.println(apNumber.getValue());
            modelShipping.setShippingApartment(apNumber.getValue());
            modelShipping.setShippingStatus(false);
            modelShipping.setShippingTaken(false);
            shippingRepository.save(modelShipping);
            modelOrder modelOrder = orderRepository.findByPaymentStatusIsFalseAndUser_IDUser(id);
            modelOrder.setPaymentStatus(true);
            //modelOrder.setShipping(modelShipping);
            modelOrder.setOrder_Date(modelOrder.getOrder_Date());
            modelOrder.setUser(modelOrder.getUser());
            orderRepository.save(modelOrder);
            Notification.show("Заказ оформлен", 3000, Notification.Position.BOTTOM_CENTER);
            UI.getCurrent().navigate(userGoodsPage.class);
        });





}
}
