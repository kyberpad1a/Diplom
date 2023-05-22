package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelFranchise;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.repo.FranchiseRepository;
import com.example.diplom.service.categoryService;
import com.example.diplom.service.franchiseService;
import com.example.diplom.view.DeniedAccessView;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Страница для просмотра и редактирования списка франшиз.
 * Реализует интерфейс BeforeEnterObserver для обработки доступа к странице с помощью Spring Security.
 */
@PageTitle("Франшизы")
@Route(value = "/franchiseInfo", layout = goodsPage.class)
public class franchiseInfo extends VerticalLayout implements BeforeEnterObserver {

    /**
     * Объект сервиса для работы с франшизами
     */
    private transient franchiseService service;

    /**
     * Идентификатор выбранной франшизы
     */
    Long id;

    /**
     * Байндер для связывания полей формы и модели франшизы
     */
    private Binder<modelFranchise> binder = new BeanValidationBinder<>(modelFranchise.class);
    /**
     * Таблица для отображения списка франшиз
     */
    Grid<modelFranchise> grid = new Grid<>(modelFranchise.class, false);
    /**
     * Флаг, указывающий, была ли нажата кнопка "Добавить"
     */
    private void submitRequest() {
        service.addFranchise(binder.getBean());
    }
    /**
     * Метод для добавления новой франшизы
     */
    private boolean pressFlag;
    /**
     * Метод для обновления данных выбранной франшизы
     */
    private void updateRequest(){
        service.updateFranchise(binder.getBean(), id);
    }
    /**
     * Метод для инициализации байндера
     */
    private void init() {
        binder.setBean(new modelFranchise());
    }
    /**
     * Создание экземпляра класса
     * @param repository   репозиторий франшиз
     * @param service      сервис для работы с франшизами
     */
    @Autowired
    public franchiseInfo(FranchiseRepository repository, franchiseService service){
        this.service = service;
        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        Label header = new Label("Добавление данных");
        TextField addFranchise = new TextField("Наименование франшизы");

        addFranchise.setSizeFull();
        Button btnAddFranchise = new Button("Добавить");
        Button btnUpdFranchise = new Button("Изменить");
        btnAddFranchise.setSizeFull();
        VerticalLayout addPopupLayout = new VerticalLayout();
        VerticalLayout updPopupLayout = new VerticalLayout();

        addPopupLayout.add(header, addFranchise, btnAddFranchise);
        addPopup.add(addPopupLayout);
        updPopup.add(updPopupLayout);
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");


        grid.addColumn(modelFranchise::getFranchise_Name).setHeader("Франшиза").setWidth("85%").setSortable(true);
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

                addPopupLayout.add(btnUpdFranchise);
                addPopupLayout.remove(btnAddFranchise);


                pressFlag = true;
                addPopup.open();

            });
            HorizontalLayout layout1 = new HorizontalLayout();
            layout1.add(btnEdit, btnDelete);
            return layout1;
        }).setTextAlign(ColumnTextAlign.END).setFrozenToEnd(true).setHeader(btnAdd);
        btnAdd.addClickListener(buttonClickEvent -> {
            if (pressFlag==true){

                header.setText("Добавление данных");
                addPopupLayout.add(btnAddFranchise);
                addPopupLayout.remove(btnUpdFranchise);


            }
            pressFlag = false;


            addPopup.open();
        });
        binder.forField(addFranchise).asRequired("Заполните поле 'Наименование франшизы'").bind(modelFranchise::getFranchise_Name, modelFranchise::setFranchise_Name);

        binder.addStatusChangeListener(e -> btnAddFranchise.setEnabled(binder.isValid()));

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

        layout.add(grid);
        add(layout);

    }
    /**
     * Метод вызывается перед входом в определенное представление.
     * @param beforeEnterEvent объект типа BeforeEnterEvent, содержащий информацию о намерении перейти к другому представлению
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("GOODSSTAFF"))) {
            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
        }
    }
}
