package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Класс для представления категории товара.
 */
@Entity
public class modelCategory {

    /**
     * Конструктор по умолчанию без параметров.
     */
    public modelCategory(){}

    /**
     * Идентификатор категории.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Category;

    /**
     * Наименование категории товара.
     */
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String Category_Name;

    /**
     * Товары, относящиеся к данной категории.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<modelGood> affectedGoods;

    /**
     * Конструктор класса с параметрами.
     *
     * @param ID_Category    идентификатор категории.
     * @param category_Name  наименование категории.
     * @param affectedGoods  товары, относящиеся к данной категории.
     */
    public modelCategory(Long ID_Category, String category_Name, Collection<modelGood> affectedGoods) {
        this.ID_Category = ID_Category;
        Category_Name = category_Name;
        this.affectedGoods = affectedGoods;
    }

    /**
     * Метод для получения идентификатора категории.
     *
     * @return идентификатор категории.
     */
    public Long getID_Category() {
        return ID_Category;
    }

    /**
     * Метод для установки идентификатора категории.
     *
     * @param ID_Category идентификатор категории.
     */
    public void setID_Category(Long ID_Category) {
        this.ID_Category = ID_Category;
    }

    /**
     * Метод для получения наименования категории.
     *
     * @return наименование категории.
     */
    public String getCategory_Name() {
        return Category_Name;
    }

    /**
     * Метод для установки наименования категории.
     *
     * @param category_Name наименование категории.
     */
    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    /**
     * Метод для получения товаров, относящихся к данной категории.
     *
     * @return товары, относящиеся к данной категории.
     */
    public Collection<modelGood> getAffectedGoods() {
        return affectedGoods;
    }

    /**
     * Метод для установки товаров, относящихся к данной категории.
     *
     * @param affectedGoods товары, относящиеся к данной категории.
     */
    public void setAffectedGoods(Collection<modelGood> affectedGoods) {
        this.affectedGoods = affectedGoods;
    }
}
