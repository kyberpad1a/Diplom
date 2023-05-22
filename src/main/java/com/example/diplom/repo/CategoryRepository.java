/*
 * Интерфейс репозитория категории
 */
package com.example.diplom.repo;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<modelCategory, Long> {

    /**
     * Поиск всех категорий, в которых есть данный товар
     *
     * @param good товар
     * @return список категорий, содержащих данный товар
     */
    public modelCategory findAllByAffectedGoods(modelGood good);
}
