package com.example.diplom.repo;

import com.example.diplom.model.modelOrderGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущности OrderGood (Товар в заказе)
 */
@Repository
public interface OrderGoodRepository extends JpaRepository<modelOrderGood, Long> {

    /**
     * Метод для поиска това в заказе по идентификатору товара и пользователя
     *
     * @param IDGood - Идентификатор товара
     * @param IDUser - Идентификатор пользователя
     * @return modelOrderGood с искомыми параметрами
     */
    modelOrderGood findByGoods_IDGoodAndOrder_User_IDUser(Long IDGood, Long IDUser);

    /**
     * Метод для поиска всех товаров в заказе определенного пользователя с определенным статусом оплаты
     *
     * @param flag - Статус оплаты
     * @param id   - Идентификатор пользователя
     * @return список объектов Collection<modelOrderGood>, содержащих информацию о товаре и его заказе
     */
    Collection<modelOrderGood> findAllByOrder_PaymentStatusAndOrder_User_IDUser(boolean flag, Long id);

}
