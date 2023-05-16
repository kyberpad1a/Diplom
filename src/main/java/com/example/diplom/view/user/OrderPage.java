package com.example.diplom.view.user;

import com.example.diplom.api.AddressModel;
import com.example.diplom.api.AsyncRestClientService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Оформить заказ")
@Route(value = "/order", layout = userPage.class)
public class OrderPage extends VerticalLayout {
    AsyncRestClientService clientService = new AsyncRestClientService();
    public OrderPage(){

        TextField addressText = new TextField("Введите адрес");
        Button btnRecieve = new Button("Получить");
        btnRecieve.addClickListener(buttonClickEvent -> {
            String value = addressText.getValue();
            //clientService.getAllComments();
            try {
                addressText.setValue(clientService.getAllCommentsAsync(value));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });
        add(addressText, btnRecieve);
        addressText.addValueChangeListener(e->{

    });


}
}
