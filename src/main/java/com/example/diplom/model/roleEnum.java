package com.example.diplom.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Перечисление, представляющее список ролей пользователей.
 */
public enum roleEnum implements GrantedAuthority {
    /**
     * Роль обычного пользователя системы.
     */
    USER,

    /**
     * Роль курьера, ответственного за доставку заказов.
     */
    COURIER,

    /**
     * Роль администратора системы.
     */
    ADMIN,

    /**
     * Роль сотрудника товарного отдела.
     */
    GOODSSTAFF;

    /**
     * Метод, возвращающий название роли.
     *
     * @return Название роли.
     */
    @Override
    public String getAuthority()
    {
        return name();
    }
}
