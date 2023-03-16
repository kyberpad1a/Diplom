package com.example.diplom.repo;

import com.example.diplom.model.modelUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository  extends CrudRepository<modelUser,Long> {
    public modelUser findByUsername(String username);


}
