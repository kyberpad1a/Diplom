package com.example.diplom.repo;

import com.example.diplom.model.modelFranchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<modelFranchise, Long> {
}
