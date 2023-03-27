package com.example.diplom.view.goods;

import com.example.diplom.model.modelCategory;
import com.example.diplom.repo.CategoryRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

@PageTitle("Категории")
@Route(value = "/categoryInfo", layout = goodsPage.class)
public class categoryInfo extends VerticalLayout {
    //@Autowired
  //  private CategoryRepository repository;
   // Iterable<modelCategory> categories = repository.findAll();
    @Autowired
    public categoryInfo(CategoryRepository repository){

        //ListDataProvider<modelCategory> dataProvider = new ListDataProvider<>(repository.findAll());
        Grid<modelCategory> grid = new Grid<>(modelCategory.class, false);
        Iterable<modelCategory> list = repository.findAll();
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(Alignment.START);
        Button btnAdd = new Button("Добавить");

        //grid.setSizeFull();
        grid.addColumn(modelCategory::getCategory_Name).setHeader("Категория").setWidth("85%");
        grid.addComponentColumn(item -> {
            Button btnEdit = new Button(new Icon(VaadinIcon.EDIT));
            Button btnDelete =new Button(new Icon(VaadinIcon.CLOSE));
            HorizontalLayout layout1 = new HorizontalLayout();
            layout1.add(btnEdit, btnDelete);
            return layout1;
        }).setTextAlign(ColumnTextAlign.END).setFrozenToEnd(true).setFooter(btnAdd);
        btnAdd.addClickListener(buttonClickEvent -> {

        });
        grid.setItems((Collection<modelCategory>) list);
        //grid.setDataProvider(dataProvider);
        layout.add(grid);
        add(layout);

    }
}
