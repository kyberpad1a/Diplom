package com.example.diplom.repo;

import com.example.diplom.model.modelPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<modelPhoto, Long> {
}
