package com.example.diplom.view.auth;

import com.example.diplom.repo.UserRepository;
import com.example.diplom.view.goods.goodsInfo;
import com.example.diplom.view.goods.goodsPage;
import com.example.diplom.view.management.manageUsers;
import com.example.diplom.view.user.userGoodsPage;
import com.example.diplom.view.user.userPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Страница авторизации пользователя.
 * Реализует UI с полями для ввода имени пользователя и пароля, кнопками для входа и регистрации, ссылкой для восстановления пароля.
 * При успешной авторизации перенаправляет на страницу в зависимости от роли пользователя (userGoodsPage, goodsInfo, manageUsers).
 */
@Route("/login")
public class loginPage extends VerticalLayout {

    /**
     * Менеджер аутентификации- позволяет аутентифицировать пользователей.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Репозиторий пользователей.
     */
    @Autowired
    final UserRepository userRepository;

    /**
     * Конструктор класса.
     *
     * @param authenticationManager Менеджер аутентификации.
     * @param userRepository Репозиторий пользователей.
     */
    public loginPage(AuthenticationManager authenticationManager, UserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.userRepository=userRepository;
        addClassName("login-rich-content");


        TextField usernameField = new TextField("Логин");
        PasswordField passwordField = new PasswordField("Пароль");


        Button loginButton = new Button("Войти");
        Button registerButton = new Button("Регистрация");


        loginButton.addClickListener(buttonClickEvent->{
            try{
                String username = usernameField.getValue();
                String password = passwordField.getValue();


                final Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

                if(authentication != null ) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);


                    if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("USER")) && userRepository.existsByActiveIsTrueAndUsername(username)) {
                        UI.getCurrent().navigate(userGoodsPage.class);
                    } else if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("GOODSSTAFF")) && userRepository.existsByActiveIsTrueAndUsername(username)){
                        UI.getCurrent().navigate(goodsInfo.class);
                    } else if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ADMIN")) && userRepository.existsByActiveIsTrueAndUsername(username)) {
                        UI.getCurrent().navigate(manageUsers.class);
                    }
                }
            } catch (AuthenticationException ex) {
                Notification.show("Неверное имя пользователя или пароль");
            }
        });


        registerButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(registrationPage.class);
        });


        H1 label = new H1("Авторизация");
        RouterLink forgotPwd = new RouterLink("Забыли пароль?", restorePage.class);


        VerticalLayout loginFormLayout = new VerticalLayout(label, usernameField, passwordField, new HorizontalLayout(loginButton, registerButton), forgotPwd);
        loginFormLayout.setHeightFull();
        loginFormLayout.setMargin(true);
        loginFormLayout.setAlignItems(Alignment.CENTER);


        HorizontalLayout loginPageLayout = new HorizontalLayout(loginFormLayout);
        loginPageLayout.setSizeFull();
        add(loginPageLayout);


        Image backgroundImage = new Image("./images/Screenshot_2.png", "Login Background");
        backgroundImage.setWidth("100%");
        backgroundImage.setHeight("650px");
        VerticalLayout imageLayout = new VerticalLayout(backgroundImage);
        imageLayout.setSizeFull();
        imageLayout.setAlignItems(Alignment.CENTER);
        loginPageLayout.add(imageLayout);
        loginPageLayout.setFlexGrow(1, imageLayout);
    }
}





