package com.example.diplom.view.user;

import com.example.diplom.view.goods.goodsPage;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.access.annotation.Secured;
@PageTitle(value = "User")
@AnonymousAllowed
@Route(value = "/user", layout = goodsPage.class)
public class user extends HorizontalLayout {
    private TextField name;
    private Button sayHello;
    public user(){
        setId("hello-world-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(FlexComponent.Alignment.END, name, sayHello);
    }

}
