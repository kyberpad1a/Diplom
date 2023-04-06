package com.example.diplom.repo;

import com.example.diplom.model.modelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<modelPhoto, Long> {
    public modelPhoto findFirstByGood_IDGood(Long ID);
    public Collection<modelPhoto> findAllByGood_IDGood(Long ID);
}
