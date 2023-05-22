package com.example.diplom.repo;

import com.example.diplom.model.modelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<modelPhoto, Long> {

    /**
     * Найти первое фото товара по его ID
     * @param ID ID товара
     * @return modelPhoto, первое фото товара
     */
    public modelPhoto findFirstByGood_IDGood(Long ID);

    /**
     * Найти все фото товара по его ID
     * @param ID ID товара
     * @return Collection<modelPhoto>, коллекция фотографий товара
     */
    public Collection<modelPhoto> findAllByGood_IDGood(Long ID);
}

