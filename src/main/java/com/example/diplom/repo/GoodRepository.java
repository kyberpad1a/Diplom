package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<modelGood, Long> {
    public Collection<modelGood> findAllByLogicalFlagFalse();
}
