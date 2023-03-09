package com.example.diplom.view.auth;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("login")
//@CssImport(value = "./login-rich-content.css")
//@Theme(variant = Lumo.DARK)
public class loginPage extends VerticalLayout {
    public loginPage(){
       // addClassName("login-rich-content");
        //loginForm.getElement().getThemeList().add("dark");
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("registration");
        Label label = new Label("Авторизация");
        HorizontalLayout btns = new HorizontalLayout(loginButton, registerButton);
        VerticalLayout loginFormLayout = new VerticalLayout(label, usernameField, passwordField, btns
        );
        loginFormLayout.setHeightFull();
        loginFormLayout.setMargin(true);

        loginFormLayout.setAlignItems(Alignment.CENTER);
        HorizontalLayout loginPageLayout = new HorizontalLayout(
                loginFormLayout
        );
        loginPageLayout.setSizeFull();
        //loginPageLayout.setHeight("500px");
        //loginPageLayout.setWidth("200px");
        //loginPageLayout.setAlignItems(Alignment.START);
        add(loginPageLayout);
        Image backgroundImage = new Image("./images/dungeon.jpg", "Login Background");
        backgroundImage.setWidth("100%");
        backgroundImage.setHeight("500px");


        // Set up the image layout
        VerticalLayout imageLayout = new VerticalLayout(
                backgroundImage
        );
        imageLayout.setSizeFull();
        imageLayout.setAlignItems(Alignment.CENTER);

        // Add the image layout to the login page layout
        loginPageLayout.add(imageLayout);
        loginPageLayout.setFlexGrow(1, imageLayout);
    }

    }




