package com.example.diplom.view.user;

import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelOrder;
import com.example.diplom.model.modelOrderGood;
import com.example.diplom.model.modelPhoto;
import com.example.diplom.repo.*;
import com.example.diplom.view.DeniedAccessView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

@Route(value = "/gooddetails", layout = userPage.class)
public class goodsDetails extends VerticalLayout implements HasUrlParameter<Long>, BeforeEnterObserver {

    @Autowired
    GoodRepository repository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserRepository userRepository;

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



//        SplitLayout splitLayout = new SplitLayout(mainImg, imgLayout);
//        splitLayout.setOrientation(SplitLayout.Orientation.VERTICAL);
//        splitLayout.setSplitterPosition(70);
//        splitLayout.setSizeFull();
//        content.add(splitLayout);
        H2 name = new H2(good.getGood_Name());
        name.setWidthFull();
        content.add(name);

//        Span price = new Span(String.format("%.2f ₽", good.getGood_Price()));
//        price.getStyle().set("font-size", "24px");
//        price.getStyle().set("color", "green");
//        content.add(price);


        TextArea description = new TextArea("Описание");
        description.setValue(good.getGood_Description());
        description.setWidthFull();
        description.setReadOnly(true);
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

        NumberField price = new NumberField("Цена");
        price.setValue(good.getGood_Price());
        price.setWidthFull();
        price.setReadOnly(true);
        content.add(price);


        Button buyButton = new Button("Добавить в корзину", event -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            id = userRepository.findByUsername(username).getIDUser();
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
            }
            else{

                if (orderGoodRepository.findByGoods_IDGoodAndOrder_User_IDUser(aLong, id)==null){
                    modelOrderGood modelOrderGood = new modelOrderGood();
                    modelOrderGood.setGoodQuantity(1);
                    modelOrderGood.setGoods(repository.findByIDGood(aLong));
                    modelOrderGood.setOrder(orderRepository.findByPaymentStatusIsFalseAndUser_IDUser(id));
                    orderGoodRepository.save(modelOrderGood);
                }
                else {
                    modelOrderGood modelOrderGood = orderGoodRepository.findByGoods_IDGoodAndOrder_User_IDUser(aLong, id);
                    modelOrderGood.setGoodQuantity(modelOrderGood.getGoodQuantity()+1);
                    modelOrderGood.setOrder(modelOrderGood.getOrder());
                    modelOrderGood.setGoods(modelOrderGood.getGoods());
                    orderGoodRepository.save(modelOrderGood);
                }
                Notification.show("Товар добавлен в корзину");
            }
        });
        content.add(buyButton);
        add(content);
    }
    public Image generateImage(byte[] path) {


        StreamResource sr = new StreamResource("good", () ->  {
            try{


                    return new ByteArrayInputStream(path);



            }
            catch (NullPointerException ex){

                return null;
            }

        });
        sr.setContentType("image/png");
        Image image = new Image();
        image.setSrc(sr);

        return image;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (beforeEnterEvent.getNavigationTarget() != DeniedAccessView.class &&
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).noneMatch(role -> role.equals("USER"))) {
            beforeEnterEvent.rerouteTo(DeniedAccessView.class);
        }
    }
    }

