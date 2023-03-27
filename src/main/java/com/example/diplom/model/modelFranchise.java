package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
public class modelFranchise {
    public modelFranchise(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Franchise;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String Franchise_Name;
    @OneToMany(mappedBy = "franchise", fetch = FetchType.EAGER)
    private Collection<modelGood> affectedGoods;

    public modelFranchise(String franchise_Name, Collection<modelGood> affectedGoods) {
        Franchise_Name = franchise_Name;
        this.affectedGoods = affectedGoods;
    }

    public Long getID_Franchise() {
        return ID_Franchise;
    }

    public void setID_Franchise(Long ID_Franchise) {
        this.ID_Franchise = ID_Franchise;
    }

    public String getFranchise_Name() {
        return Franchise_Name;
    }

    public void setFranchise_Name(String franchise_Name) {
        Franchise_Name = franchise_Name;
    }

    public Collection<modelGood> getAffectedGoods() {
        return affectedGoods;
    }

    public void setAffectedGoods(Collection<modelGood> affectedGoods) {
        this.affectedGoods = affectedGoods;
    }
}
