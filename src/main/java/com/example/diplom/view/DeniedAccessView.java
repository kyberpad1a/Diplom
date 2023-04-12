package com.example.diplom.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/accessDenied")
public class DeniedAccessView extends VerticalLayout {
    public DeniedAccessView(){
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.setAlignSelf(Alignment.CENTER);
        verticalLayout.setAlignItems(Alignment.CENTER);
        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.setSizeFull();
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setAlignSelf(Alignment.CENTER);
        horizontalLayout.add(new H1("Доступ запрещен"));
        verticalLayout.add(horizontalLayout);
        add(verticalLayout);
    }
}
