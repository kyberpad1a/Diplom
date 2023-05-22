package com.example.diplom.repo;

import com.example.diplom.model.modelFranchise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс репозитория для работы с сущностями modelFranchise.
 */
@Repository
public interface FranchiseRepository extends JpaRepository<modelFranchise, Long> {
}
