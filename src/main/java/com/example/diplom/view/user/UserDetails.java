package com.example.diplom.view.user;

import com.example.diplom.model.modelUser;
import com.example.diplom.repo.UserRepository;
import com.example.diplom.service.UserService;
import com.example.diplom.service.registrationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@PageTitle("Профиль")
@Route(value = "/userdetails", layout = userPage.class)
public class UserDetails extends VerticalLayout {

    private transient UserService service;
    Long id;
    private Binder<modelUser> binder = new BeanValidationBinder<>(modelUser.class);
    TextField surname = new TextField("Фамилия");
    TextField name = new TextField("Имя");
    TextField patronymic = new TextField("Отчество");
    EmailField email = new EmailField("Адрес эл. почты");
    private void submitRequest() {
        service.updateUserData(binder.getBean(), id);
    }

    private void init() {
        binder.setBean(new modelUser());
    }
    @Autowired
    UserRepository userRepository;
    public UserDetails(UserRepository userRepository, UserService service){
        this.userRepository=userRepository;
        this.service=service;
        init();
        VerticalLayout content = new VerticalLayout();

        TextField surname2 = new TextField("Фамилия");

        TextField name2 = new TextField("Имя");

        TextField patronymic2 = new TextField("Отчество");

        EmailField email2 = new EmailField("Адрес эл. почты");
        Button btnUpdate = new Button("Изменить");
        Button btnConfirm = new Button("Подтвердить");
        Button btnOrders = new Button("История заказов");
        surname.setEnabled(false);
        name.setEnabled(false);
        patronymic.setEnabled(false);
        email.setEnabled(false);

        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        FormLayout popupLayout = new FormLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        popupLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),

                new FormLayout.ResponsiveStep("500px", 2)
        );
        popupLayout.setColspan(btnConfirm, 2);
        popupLayout.setColspan(header1, 2);
        popupLayout.add(header1, surname2, name2, patronymic2, email2, btnConfirm);
        popupLayout.setSizeFull();
        verticalLayout.add(popupLayout);
        verticalLayout.setSizeFull();
        updPopup.add(verticalLayout);
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),

                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(btnOrders, 2);
        formLayout.add(surname, name, patronymic, email, btnUpdate, btnOrders);
        formLayout.setSizeFull();
        content.add(formLayout);

        content.setSizeFull();
        add(content);


        refresh();


        binder.forField(surname2).asRequired("Заполните поле 'Фамилия'").bind(modelUser::getSurname, modelUser::setSurname);
        binder.forField(name2).asRequired("Заполните поле 'Имя'").bind(modelUser::getName, modelUser::setName);
        binder.forField(patronymic2).bind(modelUser::getPatronymic, modelUser::setPatronymic);
        binder.forField(email2).asRequired("Заполните поле 'Адрес эл. почты'").bind(modelUser::getEmail, modelUser::setEmail);
        btnUpdate.addClickListener(buttonClickEvent -> {
            updPopup.open();
        });
        btnConfirm.addClickListener(buttonClickEvent -> {

            binder.addStatusChangeListener(e -> btnConfirm.setEnabled(binder.isValid()));
            submitRequest();
            init();
            Notification.show("Профиль изменён", 3000, Notification.Position.BOTTOM_CENTER);
            updPopup.close();
            refresh();
        });

    }
    public void refresh(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        id = userRepository.findByUsername(username).getIDUser();
        modelUser user = userRepository.findById(id).get();
        surname.setValue(user.getSurname());
        name.setValue(user.getName());
        patronymic.setValue(user.getPatronymic());
        email.setValue(user.getEmail());
    }

}
