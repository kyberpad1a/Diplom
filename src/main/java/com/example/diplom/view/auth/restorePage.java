package com.example.diplom.view.auth;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class restorePage extends VerticalLayout {
    public restorePage() {
        H1 heading = new H1("Восстановление доступа");
        TextField mailField = new TextField("Адрес эл. почты");
        mailField.setPlaceholder("Введите адрес эл. почты");
        mailField.addClassName("bordered");
        mailField.setWidth("100%");
        Button sendBtn = new Button("Восстановить");
        sendBtn.addClickListener(buttonClickEvent -> {

        });

    }
}
