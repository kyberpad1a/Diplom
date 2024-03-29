package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelUser;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.service.categoryService;
import com.example.diplom.view.DeniedAccessView;
import com.vaadin.flow.component.Key;
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

import java.util.Collection;
import java.util.List;
/**
 * Класс для отображения категорий товаров
 */
@PageTitle("Категории")
@Route(value = "/categoryInfo", layout = goodsPage.class)
public class categoryInfo extends VerticalLayout implements BeforeEnterObserver {
    /**
     * Сервис для работы с категориями товаров
     */
    private transient categoryService service;
    /**
     * Идентификатор категории
     */
    Long id;
    /**
     * Объект для связывания данных модели категории с интерфейсом пользователя
     */
    private Binder<modelCategory> binder = new BeanValidationBinder<>(modelCategory.class);
    /**
     * Флаг, указывающий на то, была ли нажата кнопка "Изменить"
     */
    private boolean pressFlag;
    /**
     * Таблица для отображения списка категорий товаров
     */
    Grid<modelCategory> grid = new Grid<>(modelCategory.class, false);

    /**
     * Метод для добавления новой категории товара
     */
    private void submitRequest() {
        service.addCategory(binder.getBean());
    }
    /**
     * Метод для обновления информации о категории товара
     */
    private void updateRequest(){
        service.updateCategory(binder.getBean(), id);
    }
    /**
     * Метод для инициализации формы добавления/изменения категории товара
     */
    private void init() {
        binder.setBean(new modelCategory());
    }
    /**
     * Конструктор класса
     *
     * @param repository Репозиторий для работы с категориями товаров
     * @param service    Сервис для работы с категориями товаров
     */
    @Autowired
    public categoryInfo(CategoryRepository repository, categoryService service){
        this.service = service;
        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header = new Label("Добавление данных");
        TextField addCategory = new TextField("Наименование категории");
        addCategory.setSizeFull();
        Button btnAddCategory = new Button("Добавить");
        Button btnUpdCategory = new Button("Изменить");
        btnAddCategory.setSizeFull();
        VerticalLayout addPopupLayout = new VerticalLayout();
        VerticalLayout updPopupLayout = new VerticalLayout();
        addPopupLayout.add(header, addCategory, btnAddCategory);
        addPopup.add(addPopupLayout);
        updPopup.add(updPopupLayout);
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");
        grid.addColumn(modelCategory::getCategory_Name).setHeader("Категория").setWidth("85%").setSortable(true);
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));
            btnDelete.addClickListener(buttonClickEvent -> {
                repository.delete(item);
                grid.setItems(repository.findAll());
            });

            btnEdit.addClickListener(buttonClickEvent -> {
                id=item.getID_Category();
                header.setText("Изменение данных");
                addPopupLayout.add(btnUpdCategory);
                addPopupLayout.remove(btnAddCategory);

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
                addPopupLayout.add(btnAddCategory);
                 addPopupLayout.remove(btnUpdCategory);

            }
            pressFlag = false;


            addPopup.open();
        });
        binder.forField(addCategory).asRequired("Заполните поле 'Наименование категории'").bind(modelCategory::getCategory_Name, modelCategory::setCategory_Name);
        binder.addStatusChangeListener(e -> btnAddCategory.setEnabled(binder.isValid()));
        binder.addStatusChangeListener(e -> btnUpdCategory.setEnabled(binder.isValid()));
        btnAddCategory.addClickShortcut(Key.ENTER);
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
        btnUpdCategory.addClickShortcut(Key.ENTER);
        btnUpdCategory.addClickListener(buttonClickEvent -> {
            try{
            updateRequest();
            init();
            grid.setItems(repository.findAll());
            Notification.show("Успешно изменено", 3000, Notification.Position.BOTTOM_CENTER);
            addPopup.close();}
            catch (DataIntegrityViolationException ex){
                Notification.show("Категория с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        init();
        grid.setItems(repository.findAll());
        layout.add(grid);
        add(layout);

    }
    /**
     * Метод, вызываемый перед открытием страницы. Проверяет аутентификацию пользователя
     * и перенаправляет на страницу с сообщением об ошибке, если пользователь не является
     * сотрудником товароведения.
     *
     * @param beforeEnterEvent событие перед входом, содержащее информацию о переходе на другую страницу
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