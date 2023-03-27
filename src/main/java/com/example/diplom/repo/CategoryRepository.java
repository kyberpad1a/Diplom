package com.example.diplom.repo;

import com.example.diplom.model.modelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends CrudRepository<modelCategory, Long> {

}
