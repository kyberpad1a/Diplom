package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelFranchise;
import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelPhoto;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.repo.FranchiseRepository;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.PhotoRepository;
import com.example.diplom.service.goodService;
import com.example.diplom.view.DeniedAccessView;
import com.vaadin.flow.component.button.Button;
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
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Страница для просмотра, добавления, изменения, удаления информации о товарах.
 */
@PageTitle("Информация о товарах")
@Route(value = "/goodsInfo", layout = goodsPage.class)
public class goodsInfo extends VerticalLayout implements BeforeEnterObserver {

    /**
     * Сервис для работы с данными товаров.
     */
    private transient goodService service;

    /**
     * Абсолютный путь к файлу фото.
     */
    private String absolutePath;

    /**
     * Максимальное количество символов в описании товара.
     */
    private int charLimit = 1000;

    /**
     * Идентификатор фото.
     */
    private Long photoID;

    /**
     * Идентификатор товара.
     */
    Long id;

    /**
     * Идентификаторы товаров.
     */
    Iterable<Long> ID;

    /**
     * Биндер для связывания модели товара с элементами пользовательского интерфейса.
     */
    private Binder<modelGood> binder = new BeanValidationBinder<>(modelGood.class);

    /**
     * Список байтов фото товара.
     */
    private List<byte[]> bytes;

    /**
     * Грид для отображения списка товаров.
     */
    Grid<modelGood> grid = new Grid<>(modelGood.class, false);

    /**
     * Модель товара для удаления.
     */
    modelGood modelGoodDelete;

    /**
     * Метод для отправки запроса на добавление товара.
     * @param bytes Список байтов фото товара.
     */
    private void submitRequest(List<byte[]> bytes) {
        service.addGood(binder.getBean(), bytes);
    }

    /**
     * Флаг для проверки нажатия кнопки добавления или изменения товара.
     */
    private boolean pressFlag;

    /**
     * Вертикальный лэйаут страницы.
     */
    private VerticalLayout layout;

    /**
     * Метод для отправки запроса на обновление товара.
     */
    private void updateRequest(){
        service.updateGood(binder.getBean(), id);
    }

    /**
     * Метод для отправки запроса на удаление товара.
     * @param good Модель товара.
     */
    private void deleteRequest(modelGood good){service.logicalDelete(good, id);}

    /**
     * Метод инициализации биндера.
     */
    private void init() {
        binder.setBean(new modelGood());
    }
    /**
     * Конструктор класса.
     * @param repository Репозиторий товаров.
     * @param service Сервис для работы с данными товаров.
     * @param franchiseRepository Репозиторий франшиз.
     * @param categoryRepository Репозиторий категорий товаров.
     * @param photoRepository Репозиторий фото товаров.
     * @param bytes Список байтов фото товара.
     */
    @Autowired
    public goodsInfo(GoodRepository repository, goodService service, @Autowired FranchiseRepository franchiseRepository, @Autowired CategoryRepository categoryRepository, PhotoRepository photoRepository, List<byte[]> bytes){
        this.service = service;
        this.bytes=bytes;

        MultiFileMemoryBuffer multiFileBuffer = new MultiFileMemoryBuffer();




        Upload multiFileUpload = new Upload(multiFileBuffer);
        multiFileUpload.setAcceptedFileTypes("image/jpeg","image/jpg", "image/png", "image/gif");
        multiFileUpload.setMaxFiles(5);

        multiFileUpload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {

                BufferedImage inputImage = ImageIO.read(multiFileBuffer.getInputStream(attachmentName));
                ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
                ImageIO.write(inputImage, "png", pngContent);
                bytes.add(pngContent.toByteArray());


            } catch (IOException e) {
                e.printStackTrace();
            }





        });

        multiFileUpload.setSizeFull();


        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        Label header = new Label("Добавление данных");
        Label label = new Label("Загрузка фото");
        TextField addGoodName = new TextField("Наименование товара");
        TextField addMaterial = new TextField("Материал");
        NumberField addPrice = new NumberField("Цена");

        Div prefix = new Div();
        prefix.setText("₽");
        addPrice.setPrefixComponent(prefix);

        addPrice.setPattern("\\d\\d\\d\\d\\d.\\d\\d");
        ComboBox<modelCategory> cbCategory = new ComboBox("Категория");
        cbCategory.setItems(categoryRepository.findAll());
        cbCategory.setItemLabelGenerator(modelCategory::getCategory_Name);
        ComboBox<modelFranchise> cbFranchise = new ComboBox("Франшиза");
        cbFranchise.setItems(franchiseRepository.findAll());
        cbFranchise.setItemLabelGenerator(modelFranchise::getFranchise_Name);
        TextArea addDescription = new TextArea("Описание");
        addDescription.setMaxLength(charLimit);
        addDescription.setValueChangeMode(ValueChangeMode.EAGER);
        addDescription.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);});

        Button btnAddGood = new Button("Добавить");
        Button btnUpdGood = new Button("Изменить");
        btnAddGood.setSizeFull();

        FormLayout addPopupLayout = new FormLayout();
        addPopupLayout.setResponsiveSteps(

                new FormLayout.ResponsiveStep("0", 1),

                new FormLayout.ResponsiveStep("500px", 2));
        addPopupLayout.setColspan(addDescription, 2);
        addPopupLayout.setColspan(multiFileUpload, 2);
        addPopupLayout.setColspan(btnAddGood, 2);
        addPopupLayout.setColspan(header, 2);
        addPopupLayout.setColspan(label, 2);
        VerticalLayout updPopupLayout = new VerticalLayout();
        addPopupLayout.add(header, addGoodName, addMaterial, addPrice, cbCategory, cbFranchise, addDescription, label, multiFileUpload, btnAddGood);
        addPopup.add(addPopupLayout);
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");


        grid.addColumn(modelGood::getGood_Name).setHeader("Название товара").setWidth("74%");

        grid.addColumn(item -> item.getCategory().getCategory_Name()).setHeader("Категория");
        grid.setItemDetailsRenderer(createGoodDetailsRenderer());
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));

            btnDelete.addClickListener(buttonClickEvent -> {
                id = item.getIDGood();

                deleteRequest(item);
                init();



                gridRefresh(repository);

            });
            btnEdit.addClickListener(buttonClickEvent -> {
                id=item.getIDGood();
                header.setText("Изменение данных");

                addPopupLayout.add(btnUpdGood);
                addPopupLayout.remove(btnAddGood);

                addPopupLayout.remove(multiFileUpload);
                addPopupLayout.remove(label);

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
                addPopupLayout.add(btnAddGood);
                addPopupLayout.remove(btnUpdGood);
                addPopupLayout.add(label);
                addPopupLayout.add(multiFileUpload);



            }
            pressFlag = false;


            addPopup.open();
        });
       binder.forField(addGoodName).asRequired("Заполните поле 'Наименование товара'").bind(modelGood::getGood_Name, modelGood::setGood_Name);
       binder.forField(addDescription).asRequired("Заполните поле 'Описание'").bind(modelGood::getGood_Description, modelGood::setGood_Description);
       binder.forField(addMaterial).asRequired("Заполните поле 'Материал'").bind(modelGood::getGood_Material, modelGood::setGood_Material);
       binder.forField(addPrice).asRequired("Заполните поле 'Цена'").withValidator(price -> price> 0.0 && price<=99999.99, "Неверный формат цены").bind(modelGood::getGood_Price, modelGood::setGood_Price);
       binder.forField(cbFranchise).asRequired("Выберите франшизу").bind(modelGood::getFranchise, modelGood::setFranchise);
       binder.forField(cbCategory).asRequired("Выберите категорию").bind(modelGood::getCategory, modelGood::setCategory);

       binder.addStatusChangeListener(e -> btnAddGood.setEnabled(binder.isValid()));
        btnAddGood.addClickListener(buttonClickEvent -> {
            try {
                submitRequest(bytes);
                bytes.clear();

                init();
                gridRefresh(repository);
                Notification.show("Успешно добавлено", 3000, Notification.Position.BOTTOM_CENTER);
                addPopup.close();
            }
            catch (DataIntegrityViolationException ex)
            {
                Notification.show("Товар с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        btnUpdGood.addClickListener(buttonClickEvent -> {
            try{
                updateRequest();
                init();

                gridRefresh(repository);
                Notification.show("Успешно изменено", 3000, Notification.Position.BOTTOM_CENTER);
                updPopup.close();}
            catch (DataIntegrityViolationException ex){
                Notification.show("Товар с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        init();

        gridRefresh(repository);

        layout.add(grid);
        add(layout);

    }
    /**
     * Обновляет таблицу товаров на основе репозитория.
     * @param repository Репозиторий товаров.
     */
    private void gridRefresh(GoodRepository repository)
    {
        try{
            if (repository.findAllByLogicalFlagFalse()!=null)
            {grid.setItems(repository.findAllByLogicalFlagFalse());}
            else {
                remove(layout);
            }
        }
        catch (NullPointerException ex){
            Notification.show("Товары отсутствуют", 10000, Notification.Position.BOTTOM_CENTER);
            grid.setItems(Collections.emptyList());
        }

    }
    /**
     * Создает компонент-рендерер для отображения подробной информации о товаре.
     * @return Возвращает компонент-рендерер для отображения подробной информации о товаре.
     */
    private static ComponentRenderer<GoodDetailsFormLayout, modelGood> createGoodDetailsRenderer() {
        return new ComponentRenderer<>(
                GoodDetailsFormLayout::new,
                GoodDetailsFormLayout::setGood);
    }
    /**
     * Обрабатывает событие перед входом в представление.
     * @param beforeEnterEvent Событие перед входом в представление.
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("GOODSSTAFF"))) {
            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
        }
    }

    /**
     * Класс формы для детальной информации о товаре.
     */
    private static class GoodDetailsFormLayout extends FormLayout {
        /**
         * Поле ввода наименования товара.
         */
        TextField addGoodName = new TextField("Наименование товара");

        /**
         * Поле ввода материала товара.
         */
        TextField addMaterial = new TextField("Материал");

        /**
         * Поле ввода цены товара.
         */
        TextField addPrice = new TextField("Цена");

        /**
         * Поле ввода франшизы товара.
         */
        TextField cbFranchise = new TextField("Франшиза");

        /**
         * Поле ввода категории товара.
         */
        TextField cbCategory = new TextField("Категория");

        /**
         * Поле ввода описания товара.
         */
        TextArea addDescription = new TextArea("Описание");


        /**
         * Блок, содержащий префикс цены.
         */
        Div prefix = new Div();

        /**
         * Конструктор формы для детальной информации о товаре.
         * Включает поля ввода для наименования, материала, цены, категории, франшизы и описания товара.
         */
        public GoodDetailsFormLayout() {
            prefix.setText("₽");
            addPrice.setPrefixComponent(prefix);
            Stream.of(addGoodName, addMaterial, addPrice, cbCategory, cbFranchise, addDescription).forEach(field -> {
                field.setReadOnly(true);
                add(field);

            });

        }
        /**
         * Устанавливает подробную информацию о товаре.
         * @param good Товар.
         */
        public void setGood(modelGood good) {
            addGoodName.setValue(good.getGood_Name());
            addDescription.setValue(good.getGood_Description());
            addMaterial.setValue(good.getGood_Material());
            addPrice.setValue(String.valueOf(good.getGood_Price()));
            cbCategory.setValue(String.valueOf(good.getCategory().getCategory_Name()));

            cbFranchise.setValue(String.valueOf(good.getFranchise().getFranchise_Name()));

        }
}}


