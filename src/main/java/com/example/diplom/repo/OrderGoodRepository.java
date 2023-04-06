package com.example.diplom.repo;

import com.example.diplom.model.modelOrderGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface OrderGoodRepository extends JpaRepository<modelOrderGood, Long> {
    modelOrderGood findByGoods_IDGoodAndOrder_User_IDUser(Long IDGood, Long IDUser);
    Collection<modelOrderGood> findAllByOrder_PaymentStatusAndOrder_User_IDUser(boolean flag, Long id);
}
