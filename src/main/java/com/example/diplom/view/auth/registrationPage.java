package com.example.diplom.view.auth;


import com.example.diplom.model.modelUser;
import com.example.diplom.model.roleEnum;
import com.example.diplom.repo.UserRepository;
import com.example.diplom.service.registrationService;
import com.example.diplom.view.user.user;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import org.hibernate.AnnotationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

@Route("/registration")
public class registrationPage extends VerticalLayout {
    private transient registrationService service;
    private Binder<modelUser> binder = new BeanValidationBinder<>(modelUser.class);

    private void submitRequest() {
        service.register(binder.getBean());
    }

    private void init() {
        binder.setBean(new modelUser());
    }

    public registrationPage(registrationService service) {
        this.service = service;
        H1 heading = new H1("Регистрация");
        Image leftImage = new Image("./images/Screenshot_2.png", "Login Background");
        Image rightImage = new Image("./images/Screenshot_2.png", "Login Background");

        // Set the size of the images
        leftImage.setWidth("50%");
        rightImage.setWidth("50%");
        leftImage.setHeight("715px");
        rightImage.setHeight("715px");


        TextField nameField = new TextField("Имя");
        nameField.setPlaceholder("Введите имя");
        nameField.addClassName("bordered");
        nameField.setWidth("100%");

        TextField surnameField = new TextField("Фамилия");
        surnameField.setPlaceholder("Введите фамилию");
        surnameField.addClassName("bordered");
        surnameField.setWidth("100%");

        TextField patronymicField = new TextField("Отчество");
        patronymicField.setPlaceholder("Введите отчество");
        patronymicField.addClassName("bordered");
        patronymicField.setWidth("100%");

        TextField username = new TextField("Логин");
        username.setPlaceholder("Введите логин");
        username.addClassName("bordered");
        username.setWidth("100%");

        PasswordField password = new PasswordField("Пароль");
        password.setPlaceholder("Введите пароль");
        password.addClassName("bordered");
        password.setWidth("100%");
       // password.setTooltipText("пароль должен быть такой-то");
        Tooltip tooltip = password.getTooltip().withManual(true);
        password.setHelperText("yhhyhu");

        PasswordField confirmPasswordField = new PasswordField("Подтвердите пароль");
        confirmPasswordField.setPlaceholder("Подтвердите пароль");
        confirmPasswordField.addClassName("bordered");
        confirmPasswordField.setWidth("100%");

        Button btnRegister = new Button("Зарегистрироваться");
        Button btnBack = new Button("Назад");

        btnRegister.addClickListener(buttonClickEvent -> {
            try {
            if (!confirmPasswordField.getValue().equals(password.getValue())) {
                Notification.show("Пароли не совпадают", 3000, Notification.Position.BOTTOM_CENTER);
            } else{
                //Notification.show(String.format("Успешная регистрация, ", binder.getBean().getUsername()));
                {
                    submitRequest();
                confirmPasswordField.setValue("");
                init();
                }
            }
            }
            catch (DataIntegrityViolationException ex){
                Notification.show("Пользователь с текущим логином уже существует");
            }
        });
        btnBack.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(loginPage.class);
        });
        binder.forField(username).asRequired("Заполните поле 'Логин'").bind(modelUser::getUsername, modelUser::setUsername);
        binder.forField(password).asRequired("Заполните поле 'Пароль'").bind(modelUser::getPassword, modelUser::setPassword);
        binder.addStatusChangeListener(e -> btnRegister.setEnabled(binder.isValid()));
        HorizontalLayout btns = new HorizontalLayout(btnRegister, btnBack);
        btns.setWidthFull();
        btns.setSizeFull();
        btns.setFlexGrow(1, btns);
        btns.setAlignItems(Alignment.CENTER);

//        VerticalLayout fieldLayout = new VerticalLayout(surnameField, nameField, patronymicField, loginField, passwordField, confirmPasswordField, btns);
        VerticalLayout fieldLayout = new VerticalLayout(heading, username, password, confirmPasswordField, btns);
        HorizontalLayout imageLayout = new HorizontalLayout(leftImage, fieldLayout, rightImage);
        imageLayout.setAlignItems(Alignment.CENTER);
        imageLayout.setSizeFull();
        add(imageLayout);
        addClassName("registration-view");
        init();
        imageLayout.setFlexGrow(1, fieldLayout);
    }
}




