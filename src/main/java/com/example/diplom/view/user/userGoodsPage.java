package com.example.diplom.view.user;

import com.example.diplom.model.modelGood;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.PhotoRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * Страница для просмотра и покупки товаров.
 */
@PageTitle("Купите нашу продукцию")
@Route(value = "/userGoodsPage", layout = userPage.class)
public class userGoodsPage extends VerticalLayout {

    /**
     * Коллекция товаров.
     */
    Collection<modelGood> goods;

    /**
     * Изображение товара.
     */
    Image image = new Image();

    /**
     * Количество загруженных товаров.
     */
    private int loadedCount = 0;

    /**
     * Новые товары.
     */
    Collection<modelGood> newGoods;

    /**
     * Количество товаров.
     */
    int itemCount = 0;

    /**
     * Репозиторий фотографий товаров.
     */
    @Autowired
    PhotoRepository repository;

    /**
     * ID товара.
     */
    Long id;

    /**
     * Создает новую страницу для просмотра и покупки товаров.
     *
     * @param repository репозиторий товаров.
     * @param goods коллекция товаров.
     */
    public userGoodsPage(GoodRepository repository, Collection<modelGood> goods){

        this.goods=goods;
        FlexLayout layout = new FlexLayout();
        Button btnLoad = new Button("Загрузить ещё");
        btnLoad.setHeight("100px");

        btnLoad.setWidthFull();
        btnLoad.getElement().getStyle().set("font-size", "50px");

        layout.setSizeFull();
        layout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        loadMoreItems(repository, layout, btnLoad);

        btnLoad.addClickListener(buttonClickEvent -> {
            loadMoreItems(repository, layout, btnLoad);
            if (repository.countAllByLogicalFlagFalse()<=loadedCount)
                btnLoad.setVisible(false);
        });


        layout.getElement().addEventListener("scroll", e -> {
            double scrollTop = layout.getElement().getProperty("scrollTop", 0d);
            double scrollHeight = layout.getElement().getProperty("scrollHeight", 0d);
            double clientHeight = layout.getElement().getProperty("clientHeight", 0d);
            if (scrollTop + clientHeight >= scrollHeight - 1) {
                Notification.show("Больше нет товаров для загрузки", 3000, Notification.Position.BOTTOM_CENTER);
                loadMoreItems(repository, layout, btnLoad);
            }
        });

    }
    /**
     * Загружает больше товаров.
     *
     * @param repository репозиторий товаров.
     * @param layout макет страницы.
     * @param btnLoad кнопка "Загрузить еще".
     */
    private void loadMoreItems(GoodRepository repository, FlexLayout layout, Button btnLoad) {
        newGoods = repository.findAllByLogicalFlagFalse(PageRequest.of(itemCount, 6));
        itemCount++;
        System.out.println("Загружено " + newGoods.size() + " новых элементов");

        for (modelGood good : newGoods) {
            VerticalLayout goodLayout = new VerticalLayout();
            RouterLink details = new RouterLink("Подробнее", goodsDetails.class, good.getIDGood());
            goodLayout.setWidth("200px");
            goodLayout.setSizeUndefined();
            Long id = good.getIDGood();
            H3 nameLabel = new H3(good.getGood_Name());
            Image image = generateImage(id);
            Label priceLabel = new Label("Цена: " + String.valueOf(good.getGood_Price()));
            image.setWidth("350px");
            image.setHeight("400px");
            goodLayout.add(image, nameLabel, priceLabel, details);
            goodLayout.setFlexGrow(1.0, nameLabel, priceLabel, details);
            goodLayout.setAlignSelf(FlexLayout.Alignment.CENTER, nameLabel);
            layout.add(goodLayout);
        }
        loadedCount += newGoods.size();
        layout.setJustifyContentMode(JustifyContentMode.AROUND);
        layout.setAlignItems(FlexLayout.Alignment.CENTER);
        add(layout, btnLoad);

        goods.addAll(newGoods);
    }

    /**
     * Генерирует изображение товара.
     *
     * @param GoodID ID товара.
     * @return изображение товара.
     */
    public Image generateImage(Long GoodID) {


        StreamResource sr = new StreamResource("good", () ->  {
            try{


                return new ByteArrayInputStream(repository.findFirstByGood_IDGood(GoodID).getPhoto_Path());
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
    /**
     * Обновляет таблицу товаров.
     *
     * @param repository репозиторий товаров.
     */
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
