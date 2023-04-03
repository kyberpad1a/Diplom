package com.example.diplom.view.user;

import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelPhoto;
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
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@PageTitle("Купите нашу продукцию")
@Route(value = "/userGoodsPage", layout = userPage.class)
public class userGoodsPage extends VerticalLayout {
    Collection<modelGood> goods;
    @Autowired
    PhotoRepository repository;
    Long id;
    public userGoodsPage(GoodRepository repository, Collection<modelGood> goods){
        this.goods=goods;
        FlexLayout layout = new FlexLayout();
        layout.setSizeFull(); // Задаем размеры контейнера
        layout.setFlexWrap(FlexLayout.FlexWrap.WRAP); // Добавляем перенос строк, чтобы изменять количество записей

// Загружаем данные из модели
        gridRefresh(repository); // Получаем список задач

// Добавляем компоненты (например, VerticalLayout) в FlexLayout для каждой задачи
        for (modelGood good : goods) {
            VerticalLayout goodLayout = new VerticalLayout();
            goodLayout.setWidth("200px"); // Ширина компонента
            goodLayout.setSizeUndefined(); // Высоту не ограничиваем, чтобы контент размера растягивал компонент
            id = good.getID_Good();
            // Создаем компоненты для отображения данных задачи
            H3 nameLabel = new H3(good.getGood_Name());
            Image image = generateImage(id);
            Label priceLabel = new Label(String.valueOf(good.getGood_Price()));
            //descriptionArea.setReadOnly(true);

            // Добавляем компоненты в компонент задачи
            goodLayout.add(nameLabel, priceLabel);

            // Устанавливаем свойства Flexbox для компонента задачи, чтобы он заполнял всю ширину FlexLayout
            goodLayout.setFlexGrow(1.0, nameLabel, priceLabel);
            goodLayout.setAlignSelf(FlexLayout.Alignment.CENTER, nameLabel);

            // Добавляем компонент задачи в FlexLayout
            layout.add(goodLayout);
        }

// Установка свойств Flexbox для FlexLayout
        layout.setJustifyContentMode(JustifyContentMode.AROUND);
        layout.setAlignItems(FlexLayout.Alignment.CENTER);

// Добавляем FlexLayout на форму
        add(layout);
    }

    public Image generateImage(Long GoodID) {

        StreamResource sr = new StreamResource("good", () ->  {
            modelPhoto photo1=repository.findFirstByGood_ID_Good(GoodID);
            return new ByteArrayInputStream(photo1.getPhoto_Path());
        });
        sr.setContentType("image/png");
        com.vaadin.flow.component.html.Image image = new com.vaadin.flow.component.html.Image(sr,"good-icon");
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
