package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<modelPhoto, Long> {
//    public modelPhoto deleteAllByGood(Long id);
//    public modelPhoto deleteAllByGoodOrderByID_PhotoDesc(Optional<modelGood> id);
//    public modelPhoto findAllByGood(Optional<modelGood> id);
}
