package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodRepository extends JpaRepository<modelGood, Long> {
    public modelGood findAllByLogicalFlagFalse();
}
