package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.PageRequest;
import java.util.Collection;
import java.util.List;

/**
 * Репозиторий для класса modelGood, используя Spring Data JPA.
 */
@Repository
public interface GoodRepository extends JpaRepository<modelGood, Long> {

    /**
     * Найти все объекты modelGood, у которых значения поля logicalFlag равно false, с использованием пагинации.
     * @param pageRequest объект, содержащий информацию о номере страницы и размере страницы.
     * @return список объектов modelGood.
     */
    public List<modelGood> findAllByLogicalFlagFalse(PageRequest pageRequest);

    /**
     * Найти все объекты modelGood, у которых значения поля logicalFlag равно false.
     * @return коллекция объектов modelGood.
     */
    public Collection<modelGood> findAllByLogicalFlagFalse();

    /**
     * Найти объект modelGood по его id.
     * @param id id объекта modelGood.
     * @return найденный объект modelGood, либо null, если такой объект не найден.
     */
    public modelGood findByIDGood(Long id);

    /**
     * Подсчитать количество объектов modelGood, у которых значения поля logicalFlag равно false.
     * @return количество объектов modelGood.
     */
    public int countAllByLogicalFlagFalse();
}

