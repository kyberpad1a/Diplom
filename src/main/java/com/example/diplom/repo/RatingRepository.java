package com.example.diplom.repo;

import com.example.diplom.model.ModelRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Интерфейс репозитория для сущности ModelRating
 */
public interface RatingRepository extends JpaRepository<ModelRating, Long> {

    /**
     * Находит все рейтинги, связанные с данным товаром
     * @param idGood идентификатор товара
     * @return коллекцию из всех рейтингов, связанных с данным товаром
     */
    Collection<ModelRating> findAllByGood_IDGood(Long idGood);
}
