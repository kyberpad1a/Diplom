package com.example.diplom.view.auth;

import com.example.diplom.view.user.user;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login")
//@CssImport(value = "./login-rich-content.css")
@Theme(variant = Lumo.DARK)
public class loginPage extends VerticalLayout {
    private final AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    public loginPage(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
       // addClassName("login-rich-content");
        //loginForm.getElement().getThemeList().add("dark");
        TextField usernameField = new TextField("Логин");
        PasswordField passwordField = new PasswordField("Пароль");
        Button loginButton = new Button("Войти");
        loginButton.addClickListener(buttonClickEvent->{
            try{

                        String username = usernameField.getValue();
            String password = passwordField.getValue();
//            Notification.show(passwordEncoder.encode(password));
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                if(authentication != null ) {

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //Access to view by role
                    if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("USER"))) {
                        UI.getCurrent().navigate(user.class);
                    } else
                        if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ADMIN"))){
                            UI.getCurrent().navigate(user.class);
                    }
                }}
            catch (AuthenticationException ex) { //
                // show default error message
                // Note: You should not expose any detailed information here like "username is known but password is wrong"
                // as it weakens security.
                Notification.show("Неверное имя пользователя или пароль");
            }


        });
//        Button loginButton = new Button("Login", buttonClickEvent -> {
//            String username = usernameField.getValue();
//            String password = passwordField.getValue();
//
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
//
//            try {
//
//                Authentication authenticated = authenticationManager.authenticate(authentication);
//
//
//                SecurityContextHolder.getContext().setAuthentication(authenticated);
//
//
//                Notification.show("aboba");
//            } catch (AuthenticationException ex) {
//                Notification.show(ex.toString(), 3000, Notification.Position.BOTTOM_CENTER);
//            }
//        });
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



        VerticalLayout imageLayout = new VerticalLayout(
                backgroundImage
        );
        imageLayout.setSizeFull();
        imageLayout.setAlignItems(Alignment.CENTER);


        loginPageLayout.add(imageLayout);
        loginPageLayout.setFlexGrow(1, imageLayout);

    }

    }




