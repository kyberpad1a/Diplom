package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Класс, представляющий собой Лицензиара (франшизу) магазина.
 */
@Entity
public class modelFranchise {

    /**
     * Создает новый экземпляр класса.
     */
    public modelFranchise(){}

    /**
     * Идентификатор лицензиара.
     * @return ID_Franchise возвращает идентификатор лицензиара.
     */
    public Long getID_Franchise() {
        return ID_Franchise;
    }

    /**
     * Устанавливает идентификатор лицензиара.
     * @param ID_Franchise принимает новый идентификатор лицензиара.
     */
    public void setID_Franchise(Long ID_Franchise) {
        this.ID_Franchise = ID_Franchise;
    }

    /**
     * Название лицензиара.
     * @return Franchise_Name возвращает название лицензиара.
     */
    public String getFranchise_Name() {
        return Franchise_Name;
    }

    /**
     * Устанавливает название лицензиара.
     * @param franchise_Name принимает новое название лицензиара.
     */
    public void setFranchise_Name(String franchise_Name) {
        Franchise_Name = franchise_Name;
    }

    /**
     * Список товаров, находящихся под действием франшизы.
     * @return affectedGoods возвращает список товаров.
     */
    public Collection<modelGood> getAffectedGoods() {
        return affectedGoods;
    }

    /**
     * Устанавливает список товаров, находящихся под действием франшизы.
     * @param affectedGoods принимает новый список товаров.
     */
    public void setAffectedGoods(Collection<modelGood> affectedGoods) {
        this.affectedGoods = affectedGoods;
    }

    /**
     * Создает новый экземпляр класса.
     * @param franchise_Name название лицензиара.
     * @param affectedGoods список товаров, находящихся под действием франшизы.
     */
    public modelFranchise(String franchise_Name, Collection<modelGood> affectedGoods) {
        Franchise_Name = franchise_Name;
        this.affectedGoods = affectedGoods;
    }

    /**
     * Идентификатор лицензиара.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Franchise;

    /**
     * Название лицензиара.
     */
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String Franchise_Name;

    /**
     * Список товаров, находящихся под действием франшизы.
     */
    @OneToMany(mappedBy = "franchise", fetch = FetchType.EAGER)
    private Collection<modelGood> affectedGoods;
}
