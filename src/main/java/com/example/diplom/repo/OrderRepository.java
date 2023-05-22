package com.example.diplom.repo;

import com.example.diplom.model.modelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderRepository extends JpaRepository<modelOrder, Long> {

    /**
     * Проверяет, существует ли заказ с заданными параметрами оплаты и пользователя.
     * @param id идентификатор пользователя.
     * @return true, если заказ существует и не оплачен, false - в противном случае.
     */
    boolean existsByPaymentStatusIsFalseAndUser_IDUser(Long id);

    /**
     * Ищет неоплаченный заказ для заданного пользователя.
     * @param id идентификатор пользователя.
     * @return неоплаченный заказ для данного пользователя.
     */
    modelOrder findByPaymentStatusIsFalseAndUser_IDUser(Long id);

    /**
     * Ищет все оплаченные заказы для заданного пользователя.
     * @param id идентификатор пользователя.
     * @return все оплаченные заказы для данного пользователя.
     */
    Collection<modelOrder> findAllByPaymentStatusIsTrueAndUser_IDUser(Long id);
}
