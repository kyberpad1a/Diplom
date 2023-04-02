package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<modelGood, Long> {
}
