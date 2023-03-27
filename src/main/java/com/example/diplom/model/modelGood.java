package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.*;

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
    @NotBlank
    @Positive
    @Digits(fraction = 2, integer = 5)
    private double Good_Price;
    @NotNull
    @NotBlank
    @Size(min = 10, max = 1000)
    private String Good_Description;
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private modelFranchise franchise;
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private modelCategory category;
}
