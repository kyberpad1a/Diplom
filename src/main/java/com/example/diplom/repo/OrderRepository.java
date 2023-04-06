package com.example.diplom.repo;

import com.example.diplom.model.modelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<modelOrder, Long> {
    boolean existsByPaymentStatusIsFalseAndUser_IDUser(Long id);
    modelOrder findByPaymentStatusIsFalseAndUser_IDUser(Long id);
}