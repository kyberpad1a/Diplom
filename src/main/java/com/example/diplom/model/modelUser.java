package com.example.diplom.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
@Entity
public class modelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDUser;
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String password;
    @NotNull
    @NotBlank
    private String surname;
    @NotNull
    @NotBlank
    private String name;
    private String patronymic;
    @Email
    @NotBlank
    @NotNull
    private String email;
    @Column(unique = true)
    private String resettoken;

    private boolean active;

    @ElementCollection(targetClass = roleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<roleEnum> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<modelOrder> orderCollection;

    public modelUser() {
    }

    public modelUser(Long IDUser, String username, String password, String surname, String name, String patronymic, String email, String resettoken, boolean active, Set<roleEnum> roles, Collection<modelOrder> orderCollection) {
        this.IDUser = IDUser;
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.resettoken = resettoken;
        this.active = active;
        this.roles = roles;
        this.orderCollection = orderCollection;
    }

    public Long getIDUser() {
        return IDUser;
    }

    public void setIDUser(Long IDUser) {
        this.IDUser = IDUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResettoken() {
        return resettoken;
    }

    public void setResettoken(String resettoken) {
        this.resettoken = resettoken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<roleEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<roleEnum> roles) {
        this.roles = roles;
    }

    public Collection<modelOrder> getOrderCollection() {
        return orderCollection;
    }

    public void setOrderCollection(Collection<modelOrder> orderCollection) {
        this.orderCollection = orderCollection;
    }
}