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
/**
 * Класс модели пользователей
 */
@Entity
public class modelUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDUser;

    /**
     * Логин пользователя
     */
    @Column(unique = true)
    @NotBlank
    @NotNull
    private String username;

    /**
     * Пароль пользователя
     */
    @NotBlank
    @NotNull
    private String password;

    /**
     * Фамилия пользователя
     */
    @NotNull
    @NotBlank
    private String surname;

    /**
     * Имя пользователя
     */
    @NotNull
    @NotBlank
    private String name;

    /**
     * Отчество пользователя
     */
    private String patronymic;

    /**
     * Электронная почта пользователя
     */
    @Email
    @NotBlank
    @NotNull
    private String email;

    /**
     * Токен для сброса пароля пользователя
     */
    @Column(unique = true)
    private String resettoken;

    /**
     * Флаг активности пользователя
     */
    private boolean active;

    /**
     * Роли пользователя
     */
    @ElementCollection(targetClass = roleEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<roleEnum> roles;

    /**
     * Коллекция заказов пользователя
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<modelOrder> orderCollection;

    /**
     * Коллекция оценок других пользователей, оставленных для данного пользователя
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<ModelRating> users;

    /**
     * Коллекция адресов доставки пользователя
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<ModelShipping> shippingCollection;

    /**
     * Конструктор по умолчанию
     */
    public modelUser() {
    }


    /**
     * Возвращает идентификатор пользователя.
     *
     * @return IDUser идентификатор пользователя.
     */
    public Long getIDUser() {
        return IDUser;
    }

    /**
     * Устанавливает идентификатор пользователя.
     *
     * @param IDUser идентификатор пользователя.
     */
    public void setIDUser(Long IDUser) {
        this.IDUser = IDUser;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return username имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param username имя пользователя.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return password пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password пароль пользователя.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает фамилию пользователя.
     *
     * @return surname фамилия пользователя.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Устанавливает фамилию пользователя.
     *
     * @param surname фамилия пользователя.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return name имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param name имя пользователя.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает отчество пользователя.
     *
     * @return patronymic отчество пользователя.
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Устанавливает отчество пользователя.
     *
     * @param patronymic отчество пользователя.
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Возвращает email пользователя.
     *
     * @return email email пользователя.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает email пользователя.
     *
     * @param email email пользователя.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает токен для сброса пароля пользователя.
     *
     * @return resettoken токен для сброса пароля.
     */
    public String getResettoken() {
        return resettoken;
    }

    /**
     * Устанавливает токен для сброса пароля пользователя.
     *
     * @param resettoken токен для сброса пароля.
     */
    public void setResettoken(String resettoken) {
        this.resettoken = resettoken;
    }

    /**
     * Проверяет, активен ли пользователь (true) или нет (false).
     *
     * @return active true, если пользователь активен, false - в противном случае.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Устанавливает значение активности для пользователя.
     *
     * @param active true, если пользователь активен, false - в противном случае.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Возвращает роли пользователя в виде набора значений перечисления.
     *
     * @return roles набор ролей пользователя.
     */
    public Set<roleEnum> getRoles() {
        return roles;
    }

    /**
     * Устанавливает роли пользователя в виде набора значений перечисления.
     *
     * @param roles набор ролей пользователя.
     */
    public void setRoles(Set<roleEnum> roles) {
        this.roles = roles;
    }

    /**
     * Возвращает коллекцию заказов, оформленных данным пользователем.
     *
     * @return orderCollection коллекция заказов.
     */
    public Collection<modelOrder> getOrderCollection() {
        return orderCollection;
    }

    /**
     * Устанавливает коллекцию заказов, оформленных данным пользователем.
     *
     * @param orderCollection коллекция заказов.
     */
    public void setOrderCollection(Collection<modelOrder> orderCollection) {
        this.orderCollection = orderCollection;
    }

    /**
     * Метод возвращает коллекцию объектов ModelRating, представляющих всех пользователей
     * @return коллекция объектов ModelRating, представляющих всех пользователей
     */
    public Collection<ModelRating> getUsers() {
        return users;
    }

    /**
     * Метод устанавливает коллекцию объектов ModelRating, представляющих всех пользователей
     * @param users коллекция объектов ModelRating, представляющих всех пользователей
     */
    public void setUsers(Collection<ModelRating> users) {
        this.users = users;
    }

    /**
     * Метод возвращает коллекцию объектов ModelShipping, представляющих все доставки
     * @return коллекция объектов ModelShipping, представляющих все доставки
     */
    public Collection<ModelShipping> getShippingCollection() {
        return shippingCollection;
    }

    /**
     * Метод устанавливает коллекцию объектов ModelShipping, представляющих все доставки
     * @param shippingCollection коллекция объектов ModelShipping, представляющих все доставки
     */
    public void setShippingCollection(Collection<ModelShipping> shippingCollection) {
        this.shippingCollection = shippingCollection;
    }

    /**
     * Конструктор класса modelUser. Создает объект modelUser с указанными параметрами.
     * @param IDUser уникальный идентификатор пользователя
     * @param username логин пользователя
     * @param password пароль пользователя
     * @param surname фамилия пользователя
     * @param name имя пользователя
     * @param patronymic отчество пользователя
     * @param email адрес электронной почты пользователя
     * @param resettoken токен сброса пароля
     * @param active статус активности пользователя
     * @param roles роли пользователя
     * @param orderCollection коллекция заказов пользователя
     * @param users коллекция объектов ModelRating, представляющих всех пользователей
     * @param shippingCollection коллекция объектов ModelShipping, представляющих все доставки
     */
    public modelUser(Long IDUser, String username, String password, String surname, String name, String patronymic, String email, String resettoken, boolean active, Set<roleEnum> roles, Collection<modelOrder> orderCollection, Collection<ModelRating> users, Collection<ModelShipping> shippingCollection) {
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
        this.users = users;
        this.shippingCollection = shippingCollection;
    }

}