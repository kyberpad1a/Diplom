package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelUser;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.service.categoryService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.List;

@PageTitle("Категории")
@Route(value = "/categoryInfo", layout = goodsPage.class)
public class categoryInfo extends VerticalLayout {
    private transient categoryService service;
    Long id;
    private Binder<modelCategory> binder = new BeanValidationBinder<>(modelCategory.class);

    Grid<modelCategory> grid = new Grid<>(modelCategory.class, false);

    private void submitRequest() {
        service.addCategory(binder.getBean());
    }

    private void updateRequest(){
        service.updateCategory(binder.getBean(), id);
    }

    private void init() {
        binder.setBean(new modelCategory());
    }
    @Autowired
    public categoryInfo(CategoryRepository repository, categoryService service){
        this.service = service;
        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        Label header = new Label("Добавление данных");
        TextField addCategory = new TextField("Наименование категории");
        TextField updCategory = new TextField("Наименование категории");
        addCategory.setSizeFull();
        Button btnAddCategory = new Button("Добавить");
        Button btnUpdCategory = new Button("Изменить");
        btnAddCategory.setSizeFull();
        VerticalLayout addPopupLayout = new VerticalLayout();
        VerticalLayout updPopupLayout = new VerticalLayout();
        updPopupLayout.add(header1, updCategory, btnUpdCategory);
        addPopupLayout.add(header, addCategory, btnAddCategory);
        addPopup.add(addPopupLayout);
        updPopup.add(updPopupLayout);
        //ListDataProvider<modelCategory> dataProvider = new ListDataProvider<>(repository.findAll());

        //Iterable<modelCategory> list = repository.findAll();
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");

        //grid.setSizeFull();
        grid.addColumn(modelCategory::getCategory_Name).setHeader("Категория").setWidth("85%");
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));
            btnDelete.addClickListener(buttonClickEvent -> {
                repository.delete(item);
                grid.setItems(repository.findAll());
            });
            btnEdit.addClickListener(buttonClickEvent -> {
                id=item.getID_Category();
                updPopup.open();

            });
            HorizontalLayout layout1 = new HorizontalLayout();
            layout1.add(btnEdit, btnDelete);
            return layout1;
        }).setTextAlign(ColumnTextAlign.END).setFrozenToEnd(true).setHeader(btnAdd);
        btnAdd.addClickListener(buttonClickEvent -> {
            addPopup.open();
        });
        binder.forField(addCategory).asRequired("Заполните поле 'Наименование категории'").bind(modelCategory::getCategory_Name, modelCategory::setCategory_Name);
        binder.forField(updCategory).asRequired("Заполните поле 'Наименование категории'").bind(modelCategory::getCategory_Name, modelCategory::setCategory_Name);
        binder.addStatusChangeListener(e -> btnAddCategory.setEnabled(binder.isValid()));
        //binder.addStatusChangeListener(e -> btnUpdCategory.setEnabled(binder.isValid()));
        btnAddCategory.addClickListener(buttonClickEvent -> {
            try {
                submitRequest();
                init();
                grid.setItems(repository.findAll());
                Notification.show("Успешно добавлено", 3000, Notification.Position.BOTTOM_CENTER);
                addPopup.close();
            }
            catch (DataIntegrityViolationException ex)
            {
                Notification.show("Категория с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        btnUpdCategory.addClickListener(buttonClickEvent -> {
            try{
            updateRequest();
            init();
            grid.setItems(repository.findAll());
            Notification.show("Успешно изменено", 3000, Notification.Position.BOTTOM_CENTER);
            updPopup.close();}
            catch (DataIntegrityViolationException ex){
                Notification.show("Категория с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        init();
        grid.setItems(repository.findAll());
        //grid.setDataProvider(dataProvider);
        layout.add(grid);
        add(layout);

    }
}