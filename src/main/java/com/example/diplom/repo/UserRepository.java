package com.example.diplom.repo;

import com.example.diplom.model.modelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<modelUser, Long> {
    modelUser findByUsername(String username);
    boolean existsByActiveIsTrueAndUsername(String username);


}
