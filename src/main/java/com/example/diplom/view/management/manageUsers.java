package com.example.diplom.view.management;

import com.example.diplom.model.modelOrderGood;
import com.example.diplom.model.modelUser;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/manageUsers", layout = managementPage.class)
public class manageUsers extends VerticalLayout {
    Grid<modelUser> grid = new Grid<>(modelUser.class, false);
    public manageUsers(){
        grid.addColumn(modelUser::getUsername).setHeader("Логин").setWidth("65%");
        grid.addColumn(item -> item.getRoles()).setHeader("Роль");

    }
}
