package com.example.diplom.view.user;

import com.example.diplom.model.modelGood;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.PhotoRepository;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

@PageTitle("Купите нашу продукцию")
@Route(value = "/userGoodsPage", layout = userPage.class)
public class userGoodsPage extends VerticalLayout {
    Collection<modelGood> goods;
    Image image = new Image();
    @Autowired
    PhotoRepository repository;
    Long id;
    public userGoodsPage(GoodRepository repository, Collection<modelGood> goods){
        this.goods=goods;
        FlexLayout layout = new FlexLayout();
        layout.setSizeFull(); // Задаем размеры контейнера
        layout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        gridRefresh(repository);

        for (modelGood good : goods) {

            VerticalLayout goodLayout = new VerticalLayout();
            RouterLink details = new RouterLink("Подробнее", goodsDetails.class, good.getIDGood());
            goodLayout.setWidth("200px");
            goodLayout.setSizeUndefined();
            id = good.getIDGood();
            H3 nameLabel = new H3(good.getGood_Name());
            image = generateImage(id);


            Label priceLabel = new Label("Цена: " + String.valueOf(good.getGood_Price()));
            image.setWidth("350px");
            image.setHeight("400px");
            //descriptionArea.setReadOnly(true);

            // Добавляем компоненты в компонент задачи
            goodLayout.add(image, nameLabel, priceLabel, details);
            goodLayout.setFlexGrow(1.0, nameLabel, priceLabel, details);
            goodLayout.setAlignSelf(FlexLayout.Alignment.CENTER, nameLabel);
            layout.add(goodLayout);
        }
        layout.setJustifyContentMode(JustifyContentMode.AROUND);
        layout.setAlignItems(FlexLayout.Alignment.CENTER);
        add(layout);

    }

    public Image generateImage(Long GoodID) {


        StreamResource sr = new StreamResource("good", () ->  {
            try{


                return new ByteArrayInputStream(repository.findFirstByGood_IDGood(GoodID).getPhoto_Path());
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

    private void gridRefresh(GoodRepository repository)
    {
        try{
            if (repository.findAllByLogicalFlagFalse()!=null)
            {goods.addAll(repository.findAllByLogicalFlagFalse());}
        }
        catch (NullPointerException ex){
            Notification.show("Товары отсутствуют", 10000, Notification.Position.BOTTOM_CENTER);
            goods.clear();
        }

    }
}
