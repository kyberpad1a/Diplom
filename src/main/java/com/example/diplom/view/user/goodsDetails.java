package com.example.diplom.view.user;

import com.example.diplom.model.*;
import com.example.diplom.repo.*;
import com.example.diplom.view.DeniedAccessView;
import com.example.diplom.view.auth.loginPage;
import com.example.diplom.view.auth.registrationPage;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@PageTitle("Детали товара")
@Route(value = "/gooddetails", layout = userPage.class)
public class goodsDetails extends VerticalLayout implements HasUrlParameter<Long>
//        , BeforeEnterObserver
{

    @Autowired
    GoodRepository repository;

    private Binder<ModelRating> binder = new BeanValidationBinder<>(ModelRating.class);


    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GoodRepository goodRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderGoodRepository orderGoodRepository;

    private Long id;

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long aLong) {


        modelGood good = repository.findByIDGood(aLong);
        VerticalLayout content = new VerticalLayout();
        content.setAlignSelf(Alignment.CENTER);
        content.setAlignItems(Alignment.CENTER);
        VerticalLayout orderLayout = new VerticalLayout();

        H3 name = new H3(good.getGood_Name());

        //name.setWidthFull();
        orderLayout.add(name);

        Span price = new Span(String.format("%.2f ₽", good.getGood_Price()));
        price.getStyle().set("font-size", "24px");
        //price.getStyle().set("color", "green");
        orderLayout.add(price);

        content.setWidthFull();
        Image mainImg = new Image();
        HorizontalLayout imgLayout = new HorizontalLayout();
        Scroller scroller = new Scroller();
        imgLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        Collection<modelPhoto> photos = photoRepository.findAllByGood_IDGood(aLong);
        for (modelPhoto photo:photos) {


            Image img = new Image();
            img = generateImage(photo.getPhoto_Path());
            img.setHeight("250px");
            img.setWidth("200px");
            imgLayout.add(img);
            img = null;

//            Image finalImg = img;
//            img.addClickListener(imageClickEvent -> {
//                mainImg.add(finalImg);
//            });
        }
        scroller.setContent(imgLayout);
        scroller.setWidthFull();
        content.add(scroller);
        Button buyButton = new Button("Добавить в корзину", event -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            id = userRepository.findByUsername(username).getIDUser();
            if (id!=null) {
                if (!orderRepository.existsByPaymentStatusIsFalseAndUser_IDUser(id)) {
                    modelOrder modelOrder = new modelOrder();
                    LocalDate localDate = LocalDate.now();
                    Date date = Date.valueOf(localDate);
                    modelOrder.setOrder_Date(date);
                    modelOrder.setUser(userRepository.findByUsername(username));
                    modelOrder.setPaymentStatus(false);
                    orderRepository.save(modelOrder);

                    modelOrderGood modelOrderGood = new modelOrderGood();
                    modelOrderGood.setGoodQuantity(1);
                    modelOrderGood.setGoods(repository.findByIDGood(aLong));
                    modelOrderGood.setOrder(orderRepository.findByPaymentStatusIsFalseAndUser_IDUser(id));
                    orderGoodRepository.save(modelOrderGood);
                    // Notification.show(username);
                    Notification.show("Товар добавлен в корзину!");
                } else {

                    if (orderGoodRepository.findByGoods_IDGoodAndOrder_User_IDUser(aLong, id) == null) {
                        modelOrderGood modelOrderGood = new modelOrderGood();
                        modelOrderGood.setGoodQuantity(1);
                        modelOrderGood.setGoods(repository.findByIDGood(aLong));
                        modelOrderGood.setOrder(orderRepository.findByPaymentStatusIsFalseAndUser_IDUser(id));
                        orderGoodRepository.save(modelOrderGood);
                    } else {
                        modelOrderGood modelOrderGood = orderGoodRepository.findByGoods_IDGoodAndOrder_User_IDUser(aLong, id);
                        modelOrderGood.setGoodQuantity(modelOrderGood.getGoodQuantity() + 1);
                        modelOrderGood.setOrder(modelOrderGood.getOrder());
                        modelOrderGood.setGoods(modelOrderGood.getGoods());
                        orderGoodRepository.save(modelOrderGood);
                    }
                    Notification.show("Товар добавлен в корзину");
                }
            } else
                UI.getCurrent().navigate(loginPage.class);
        });

        SplitLayout splitLayout = new SplitLayout(imgLayout, orderLayout);
        splitLayout.setOrientation(SplitLayout.Orientation.HORIZONTAL);
        splitLayout.setSplitterPosition(70);
        splitLayout.setSizeFull();
        content.add(splitLayout);

        orderLayout.add(buyButton);
        //orderLayout.setAlignSelf(Alignment.END);
        orderLayout.setAlignItems(Alignment.CENTER);
        imgLayout.setAlignSelf(Alignment.STRETCH);
        //imgLayout.add(orderLayout);
        //content.add(buyButton);








        TextArea description = new TextArea("Описание");
        description.setValue(good.getGood_Description());
        description.setWidthFull();
        description.setReadOnly(true);
        description.getStyle().set("border-color", "transparent");
        content.add(description);

        TextField material = new TextField("Материал");
        material.setValue(good.getGood_Material());
        material.setWidthFull();
        material.setReadOnly(true);
        content.add(material);

        TextField franchise = new TextField("Франшиза");
        franchise.setValue(good.getFranchise().getFranchise_Name());
        franchise.setWidthFull();
        franchise.setReadOnly(true);
        content.add(franchise);

        TextField category = new TextField("Категория");
        category.setValue(good.getCategory().getCategory_Name());
        category.setWidthFull();
        category.setReadOnly(true);
        content.add(category);

//        NumberField price = new NumberField("Цена");
//        price.setValue(good.getGood_Price());
//        price.setWidthFull();
//        price.setReadOnly(true);
//        content.add(price);


        Button btnRating = new Button("Добавить отзыв", buttonClickEvent -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Long id= userRepository.findByUsername(authentication.getName()).getIDUser();
            if (id!=null){
                Dialog ratingPopup = new Dialog();
                IntegerField value = new IntegerField("Оценка");
                Button btnConfirm = new Button("Подтвердить");
                value.setMax(5);
                value.setMin(1);
                value.setStep(1);
                value.setStepButtonsVisible(true);
                TextArea ratingText = new TextArea("Отзыв");
                ratingText.setMaxLength(500);
                ratingText.addValueChangeListener(e -> {
                    e.getSource()
                            .setHelperText(e.getValue().length() + "/" + 500);});
                ratingText.setWidthFull();
                ratingPopup.add(value, ratingText, btnConfirm);
                binder.forField(ratingText).asRequired("Заполните поле 'Отзыв'").bind(ModelRating::getRatingText, ModelRating::setRatingText);
                binder.forField(value).asRequired("Заполните поле 'Оценка'").withValidator(rate -> rate > 0 && rate<=5, "Неверный формат цены").bind(ModelRating::getRatingValue, ModelRating::setRatingValue);
                
                binder.addStatusChangeListener(e -> btnConfirm.setEnabled(binder.isValid()));
                btnConfirm.addClickListener(buttonClickEvent1 -> {
                    ModelRating modelRating = new ModelRating();
                    modelRating.setRatingText(ratingText.getValue());
                    modelRating.setRatingValue(value.getValue());
                    modelRating.setUser(userRepository.findByUsername(authentication.getName()));
                    modelRating.setGood(goodRepository.findByIDGood(aLong));
                    ratingRepository.save(modelRating);
                    Notification.show("Успешно добавлено", 3000, Notification.Position.BOTTOM_CENTER);
                    ratingPopup.close();

                });
                ratingPopup.open();

            }
            else
                UI.getCurrent().navigate(loginPage.class);
        });
        add(content, btnRating);
        Collection<ModelRating> modelRating = ratingRepository.findAllByGood_IDGood(aLong);
        for (ModelRating rating:modelRating)
        {
            VerticalLayout loginAndRatingText = new VerticalLayout();
            VerticalLayout ratingValue = new VerticalLayout();
            ratingValue.setAlignItems(Alignment.CENTER);
            H3 login = new H3(rating.getUser().getUsername());
            TextArea textArea = new TextArea();
            textArea.setValue(rating.getRatingText());
            textArea.setReadOnly(true);
            Span ratingVal = new Span(String.format("%d из 5", rating.getRatingValue()));
            ratingVal.getStyle().set("font-size", "40px");
            textArea.setWidthFull();
            loginAndRatingText.add(login, textArea);
            loginAndRatingText.setAlignItems(Alignment.START);
            ratingValue.add(ratingVal);
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidthFull();
            layout.add(loginAndRatingText, ratingValue);
            layout.setAlignSelf(Alignment.STRETCH);
            layout.setAlignItems(Alignment.STRETCH);

            add(layout);

        }


    }
    public Image generateImage(byte[] path) {


        StreamResource sr = new StreamResource("good", () ->  {
            try{


                    return new ByteArrayInputStream(path);



            }
            catch (NullPointerException ex){

                try {
                    return new ByteArrayInputStream(IOUtils.toByteArray(Objects.requireNonNull(getClass().getResourceAsStream("/undefinedPhoto.png"))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        sr.setContentType("image/png");
        Image image = new Image();
        image.setSrc(sr);

        return image;
    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
//                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("USER"))) {
//            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
//        }
//    }
    }

