package com.example.diplom.view.user;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/user")
public class user extends VerticalLayout {
    public user(){
        Label label = new Label("Авторизация");
        add(label);
    }

}
