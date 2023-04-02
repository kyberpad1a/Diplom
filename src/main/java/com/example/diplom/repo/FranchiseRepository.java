package com.example.diplom.repo;

import com.example.diplom.model.modelFranchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<modelFranchise, Long> {
}
