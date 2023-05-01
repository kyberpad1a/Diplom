package com.example.diplom.view.management;

import com.example.diplom.model.*;
import com.example.diplom.repo.*;
import com.example.diplom.service.UserService;
import com.example.diplom.service.goodService;
import com.example.diplom.view.DeniedAccessView;
import com.example.diplom.view.auth.loginPage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@PageTitle("Управление пользователями")
@Route(value = "/manageUsers", layout = managementPage.class)
public class manageUsers extends VerticalLayout implements BeforeEnterObserver {
    private UserRepository repository;
    Long id;
    String flag;
    boolean boolflag;
    Checkbox cbActive;
    private transient UserService service;
    VerticalLayout layout = new VerticalLayout();
    private Binder<modelUser> binder = new BeanValidationBinder<>(modelUser.class);
    private void init() {
        binder.setBean(new modelUser());
    }
    private void updateRequest(){
        service.updateUser(binder.getBean(), id, flag, boolflag);
    }
    Grid<modelUser> grid = new Grid<>(modelUser.class, false);
    @Autowired
    public manageUsers(UserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;




        grid.addColumn(modelUser::getUsername).setHeader("Имя пользователя").setWidth("60%");
        grid.addComponentColumn(item->{
            ComboBox<roleEnum> cbRole = new ComboBox<>();
            cbRole.setItems(roleEnum.values());

            cbRole.setValue(item.getRoles().iterator().next());
            cbRole.addValueChangeListener(e ->{
                id = item.getIDUser();
                switch (cbRole.getValue()){
                    case USER -> flag = "USER";
                    case ADMIN -> flag = "ADMIN";
                    case COURIER -> flag = "COURIER";
                    case GOODSSTAFF -> flag = "GOODSSTAFF";
                }
                try {
                    updateRequest();
                }
                catch(DataIntegrityViolationException ex){
                    Notification.show("Изменение не завершено", 3000, Notification.Position.BOTTOM_CENTER);
                }
            });

            return cbRole;
        }).setHeader("Роль").setWidth("25%");
        grid.addComponentColumn(item ->{
            Checkbox cbActive = new Checkbox();
            //binder.forField(cbActive).bind(modelUser::isActive, modelUser::setActive);
            cbActive.setValue(item.isActive());

            cbActive.addValueChangeListener(e -> {

                id = item.getIDUser();

                if (cbActive.getValue()) {
                    boolflag = true;
                }
                else
                    boolflag =false;
                try {
                    updateRequest();
                }
                catch(DataIntegrityViolationException ex){
                    Notification.show("Изменение не завершено", 3000, Notification.Position.BOTTOM_CENTER);
                }
            });
            return cbActive;
        }).setHeader("Активен?");
        //grid.setItemDetailsRenderer(createGoodDetailsRenderer());







        init();

        gridRefresh(repository);
        //grid.setDataProvider(dataProvider);
        layout.add(grid);
        add(layout);

    }

    private void gridRefresh(UserRepository repository)
    {
        try{
            if (repository.findAll()!=null)
            {grid.setItems(repository.findAll());}
            else {
                remove(layout);
            }
        }
        catch (NullPointerException ex){
            Notification.show("Пользователи отсутствуют", 10000, Notification.Position.BOTTOM_CENTER);
            grid.setItems(Collections.emptyList());
        }

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("ADMIN"))) {
            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
        }
    }
}
