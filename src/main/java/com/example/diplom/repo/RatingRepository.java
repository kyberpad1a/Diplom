package com.example.diplom.repo;

import com.example.diplom.model.ModelRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RatingRepository extends JpaRepository<ModelRating, Long> {
    Collection<ModelRating> findAllByGood_IDGood(Long idGood);
}
