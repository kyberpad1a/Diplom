package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelFranchise;
import com.example.diplom.model.modelGood;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.repo.FranchiseRepository;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.service.franchiseService;
import com.example.diplom.service.goodService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
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
import com.vaadin.flow.component.upload.receivers.MultiFileBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import java.util.Locale;
import java.util.stream.Stream;

@PageTitle("Информация о товарах")
@Route(value = "/goodsInfo", layout = goodsPage.class)
public class goodsInfo extends VerticalLayout {
    private transient goodService service;
    private String absolutePath;
    private int charLimit = 1000;
    Long id;
    private Binder<modelGood> binder = new BeanValidationBinder<>(modelGood.class);

    Grid<modelGood> grid = new Grid<>(modelGood.class, false);

    private void submitRequest() {
        service.addGood(binder.getBean());
    }
    private boolean pressFlag;

    private void updateRequest(){
        service.updateGood(binder.getBean(), id);
    }

    private void init() {
        binder.setBean(new modelGood());
    }
    @Autowired
    public goodsInfo(GoodRepository repository, goodService service, @Autowired FranchiseRepository franchiseRepository, @Autowired CategoryRepository categoryRepository){
        this.service = service;

        MultiFileBuffer multiFileBuffer = new MultiFileBuffer();
        Upload multiFileUpload = new Upload(multiFileBuffer);
        multiFileUpload.addSucceededListener(event -> {
            // Determine which file was uploaded successfully
            String uploadFileName = event.getFileName();
            // Get information for that specific file
            FileData savedFileData = multiFileBuffer
                    .getFileData(uploadFileName);
            absolutePath = savedFileData.getFile().getAbsolutePath();


        });
        multiFileUpload.setMaxFileSize(200);
        multiFileUpload.setSizeFull();
        multiFileUpload.setAcceptedFileTypes(".jpeg", ".png");

        Dialog addPopup = new Dialog();
        Dialog updPopup = new Dialog();
        Label header1 = new Label("Изменение данных");
        Label header = new Label("Добавление данных");
        Label label = new Label("Загрузка фото");
        TextField addGoodName = new TextField("Наименование товара");
        TextField addMaterial = new TextField("Материал");
        NumberField addPrice = new NumberField("Цена");
//        addPrice.addValueChangeListener(event -> {
//            String value = event.getValue().toString();
//            if (value.length()>1){
//            if (value.startsWith("0")) {
//                Notification.show("Первая цифра не должна быть нулем",
//                        3000, Notification.Position.BOTTOM_CENTER);
//                // установить предыдущее корректное значение
//                event.getSource().setValue(event.getOldValue());
//            }}
//        });
//        addPrice.setValue(null);
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
       // TextField updFranchise = new TextField("Наименование франшизы");
        //addFranchise.setSizeFull();
        Button btnAddGood = new Button("Добавить");
        Button btnUpdGood = new Button("Изменить");
        btnAddGood.setSizeFull();

        FormLayout addPopupLayout = new FormLayout();
        addPopupLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
        addPopupLayout.setColspan(addDescription, 2);
        addPopupLayout.setColspan(btnAddGood, 2);
        addPopupLayout.setColspan(header, 2);
        addPopupLayout.setColspan(label, 2);
        VerticalLayout updPopupLayout = new VerticalLayout();
        //updPopupLayout.add(header1, updFranchise, btnUpdFranchise);
        addPopupLayout.add(header, addGoodName, addMaterial, addPrice, cbCategory, cbFranchise, addDescription, label, multiFileUpload, btnAddGood);
        addPopup.add(addPopupLayout);
        //updPopup.add(updPopupLayout);
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");

        //grid.setSizeFull();
        grid.addColumn(modelGood::getGood_Name).setHeader("Название товара");
        //grid.addColumn(modelGood::getFranchise).setHeader("Франшиза");
        grid.addColumn(modelGood::getCategory).setHeader("Категория");
        grid.setItemDetailsRenderer(createGoodDetailsRenderer());
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));
            btnDelete.addClickListener(buttonClickEvent -> {
                repository.delete(item);
                grid.setItems(repository.findAll());
            });
            btnEdit.addClickListener(buttonClickEvent -> {
                id=item.getID_Good();
                header.setText("Изменение данных");
                //addPopupLayout.add(header1);
                addPopupLayout.add(btnUpdGood);
                addPopupLayout.remove(btnAddGood);
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
                addPopupLayout.add(btnAddGood);
                addPopupLayout.remove(btnUpdGood);
                //addPopupLayout.remove(header1);

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
                submitRequest();
                init();
                grid.setItems(repository.findAll());
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
                grid.setItems(repository.findAll());
                Notification.show("Успешно изменено", 3000, Notification.Position.BOTTOM_CENTER);
                updPopup.close();}
            catch (DataIntegrityViolationException ex){
                Notification.show("Товар с текущим наименованием уже существует", 3000, Notification.Position.BOTTOM_CENTER);
            }
        });
        init();
        grid.setItems(repository.findAll());
        //grid.setDataProvider(dataProvider);
        layout.add(grid);
        add(layout);

    }
    private static ComponentRenderer<GoodDetailsFormLayout, modelGood> createGoodDetailsRenderer() {
        return new ComponentRenderer<>(
                GoodDetailsFormLayout::new,
                GoodDetailsFormLayout::setGood);
    }

    private static class GoodDetailsFormLayout extends FormLayout {
        TextField addGoodName = new TextField("Наименование товара");
        TextField addMaterial = new TextField("Материал");
        TextField addPrice = new TextField("Цена");
        TextField cbFranchise = new TextField("Франшиза");
        TextField cbCategory = new TextField("Категория");
        TextArea addDescription = new TextArea("Описание");
        Div prefix = new Div();


        public GoodDetailsFormLayout() {
            prefix.setText("₽");
            addPrice.setPrefixComponent(prefix);
            Stream.of(addGoodName, addMaterial, addPrice, cbCategory, cbFranchise, addDescription).forEach(field -> {
                field.setReadOnly(true);
                add(field);
            });

        }

        public void setGood(modelGood good) {
            addGoodName.setValue(good.getGood_Name());
            addDescription.setValue(good.getGood_Description());
            addMaterial.setValue(good.getGood_Material());
            addPrice.setValue(String.valueOf(good.getGood_Price()));
            cbCategory.setValue(String.valueOf(good.getCategory()));
           // cbCategory.setItemLabelGenerator(modelCategory::getCategory_Name);
            cbFranchise.setValue(String.valueOf(good.getFranchise()));
           // cbFranchise.setItemLabelGenerator(modelFranchise::getFranchise_Name);
        }
}}


