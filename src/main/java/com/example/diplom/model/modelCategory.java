package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class modelCategory {
    public modelCategory(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Category;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String Category_Name;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Collection<modelGood> affectedGoods;

    public modelCategory(Long ID_Category, String category_Name, Collection<modelGood> affectedGoods) {
        this.ID_Category = ID_Category;
        Category_Name = category_Name;
        this.affectedGoods = affectedGoods;
    }

    public Long getID_Category() {
        return ID_Category;
    }

    public void setID_Category(Long ID_Category) {
        this.ID_Category = ID_Category;
    }

    public String getCategory_Name() {
        return Category_Name;
    }

    public void setCategory_Name(String category_Name) {
        Category_Name = category_Name;
    }

    public Collection<modelGood> getAffectedGoods() {
        return affectedGoods;
    }

    public void setAffectedGoods(Collection<modelGood> affectedGoods) {
        this.affectedGoods = affectedGoods;
    }
}
