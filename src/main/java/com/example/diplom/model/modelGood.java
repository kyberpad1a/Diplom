package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
public class modelGood {
    public modelGood(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Good;
    @NotNull
    @NotBlank
    private String Good_Name;
    @NotNull
    @NotBlank
    private String Good_Material;
    @NotNull

    @Positive
    @Digits(fraction = 2, integer = 5)
    private double Good_Price;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 1000)
    private String Good_Description;

    private boolean logicalFlag;
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelFranchise franchise;
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelCategory category;

    @OneToMany(mappedBy = "good", fetch = FetchType.EAGER)
    private Collection<modelPhoto> usedPhotos;

    public Long getID_Good() {
        return ID_Good;
    }

    public void setID_Good(Long ID_Good) {
        this.ID_Good = ID_Good;
    }

    public String getGood_Name() {
        return Good_Name;
    }

    public void setGood_Name(String good_Name) {
        Good_Name = good_Name;
    }

    public String getGood_Material() {
        return Good_Material;
    }

    public void setGood_Material(String good_Material) {
        Good_Material = good_Material;
    }

    public double getGood_Price() {
        return Good_Price;
    }

    public void setGood_Price(double good_Price) {
        Good_Price = good_Price;
    }

    public String getGood_Description() {
        return Good_Description;
    }

    public void setGood_Description(String good_Description) {
        Good_Description = good_Description;
    }

    public modelFranchise getFranchise() {
        return franchise;
    }

    public void setFranchise(modelFranchise franchise) {
        this.franchise = franchise;
    }

    public modelCategory getCategory() {
        return category;
    }

    public void setCategory(modelCategory category) {
        this.category = category;
    }

    public Collection<modelPhoto> getUsedPhotos() {
        return usedPhotos;
    }

    public void setUsedPhotos(Collection<modelPhoto> usedPhotos) {
        this.usedPhotos = usedPhotos;
    }

    public boolean isLogicalFlag() {
        return logicalFlag;
    }

    public void setLogicalFlag(boolean logicalFlag) {
        this.logicalFlag = logicalFlag;
    }

    public modelGood(Long ID_Good, String good_Name, String good_Material, double good_Price, String good_Description, boolean logicalFlag, modelFranchise franchise, modelCategory category, Collection<modelPhoto> usedPhotos) {
        this.ID_Good = ID_Good;
        Good_Name = good_Name;
        Good_Material = good_Material;
        Good_Price = good_Price;
        Good_Description = good_Description;
        this.logicalFlag = logicalFlag;
        this.franchise = franchise;
        this.category = category;
        this.usedPhotos = usedPhotos;
    }
}
