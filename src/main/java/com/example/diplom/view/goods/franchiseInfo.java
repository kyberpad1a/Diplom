package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelFranchise;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.repo.FranchiseRepository;
import com.example.diplom.service.categoryService;
import com.example.diplom.service.franchiseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;

@PageTitle("Франшизы")
@Secured("GOODSSTAFF")
@Route(value = "/franchiseInfo", layout = goodsPage.class)
public class franchiseInfo extends VerticalLayout {
    private transient franchiseService service;
    Long id;
    private Binder<modelFranchise> binder = new BeanValidationBinder<>(modelFranchise.class);

    Grid<modelFranchise> grid = new Grid<>(modelFranchise.class, false);

    private void submitRequest() {
        service.addFranchise(binder.getBean());
    }
    private boolean pressFlag;

    private void updateRequest(){
        service.updateFranchise(binder.getBean(), id);
    }

    private void init() {
        binder.setBean(new modelFranchise());
    }
    @Autowired
    public franchiseInfo(FranchiseRepository repository, franchiseService service){
        this.service = service;
        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        Label header = new Label("Добавление данных");
        TextField addFranchise = new TextField("Наименование франшизы");
       // TextField updFranchise = new TextField("Наименование франшизы");
        addFranchise.setSizeFull();
        Button btnAddFranchise = new Button("Добавить");
        Button btnUpdFranchise = new Button("Изменить");
        btnAddFranchise.setSizeFull();
        VerticalLayout addPopupLayout = new VerticalLayout();
        VerticalLayout updPopupLayout = new VerticalLayout();
        //updPopupLayout.add(header1, updFranchise, btnUpdFranchise);
        addPopupLayout.add(header, addFranchise, btnAddFranchise);
        addPopup.add(addPopupLayout);
        updPopup.add(updPopupLayout);
        //ListDataProvider<modelCategory> dataProvider = new ListDataProvider<>(repository.findAll());

        //Iterable<modelCategory> list = repository.findAll();
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");

        //grid.setSizeFull();
        grid.addColumn(modelFranchise::getFranchise_Name).setHeader("Франшиза").setWidth("85%");
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));
            btnDelete.addClickListener(buttonClickEvent -> {
                repository.delete(item);
                grid.setItems(repository.findAll());
            });
            btnEdit.addClickListener(buttonClickEvent -> {
                id=item.getID_Franchise();
                header.setText("Изменение данных");
                //addPopupLayout.add(header1);
                addPopupLayout.add(btnUpdFranchise);
                addPopupLayout.remove(btnAddFranchise);
                //addPopupLayout.remove(header1);

                pressFlag = true;
                addPopup.open();

            });
            HorizontalLayout layout1 = new HorizontalLayout();
            layout1.add(btnEdit, btnDelete);
            return layout1;
        }).setTextAlign(ColumnTextAlign.END).setFrozenToEnd(true).setHeader(btnAdd);
        btnAdd.addClickListener(buttonClickEvent -> {
            if (pressFlag==true){
                //addPopupLayout.add(header);
                header.setText("Добавление данных");
                addPopupLayout.add(btnAddFranchise);
                addPopupLayout.remove(btnUpdFranchise);
                //addPopupLayout.remove(header1);

            }
            pressFlag = false;


            addPopup.open();
        });
        binder.forField(addFranchise).asRequired("Заполните поле 'Наименование франшизы'").bind(modelFranchise::getFranchise_Name, modelFranchise::setFranchise_Name);
       // binder.forField(updFranchise).asRequired("Заполните поле 'Наименование франшизы'").bind(modelFranchise::getFranchise_Name, modelFranchise::setFranchise_Name);
        binder.addStatusChangeListener(e -> btnAddFranchise.setEnabled(binder.isValid()));
        //binder.addStatusChangeListener(e -> btnUpdFranchise.setEnabled(binder.isValid()));
        btnAddFranchise.addClickListener(buttonClickEvent -> {
            try {
                submitRequest();
                init();
                grid.setItems(repository.findAll());
                Notification.show("Успешно добавлено", 3000, Notification.Position.BOTTOM_CENTER);
                addPopup.close();
            }
            catch (DataIntegrityViolationException ex)
            {
                Notification.show("Франшиза с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        btnUpdFranchise.addClickListener(buttonClickEvent -> {
            try{
                updateRequest();
                init();
                grid.setItems(repository.findAll());
                Notification.show("Успешно изменено", 3000, Notification.Position.BOTTOM_CENTER);
                updPopup.close();}
            catch (DataIntegrityViolationException ex){
                Notification.show("Франшиза с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        init();
        grid.setItems(repository.findAll());
        //grid.setDataProvider(dataProvider);
        layout.add(grid);
        add(layout);

    }
}
