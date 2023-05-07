package com.example.diplom.service;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelUser;
import com.example.diplom.model.roleEnum;
import com.example.diplom.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    public void updateUser(modelUser user, Long id, String flag){
        modelUser originalmodel = repository.findById(id).orElse(null);
        if(user != null){
            if (flag!=null)
            {switch (flag){
                case "ADMIN" -> originalmodel.setRoles(Collections.singleton(roleEnum.ADMIN));
                case "USER" -> originalmodel.setRoles(Collections.singleton(roleEnum.USER));
                case "COURIER" -> originalmodel.setRoles(Collections.singleton(roleEnum.COURIER));
                case "GOODSSTAFF" -> originalmodel.setRoles(Collections.singleton(roleEnum.GOODSSTAFF));
            }
            }
            repository.save(originalmodel);
        }
    }

    public void updateUserActive(modelUser user, Long id, boolean boolflag){
        modelUser originalmodel = repository.findById(id).orElse(null);
        if(user != null){
            originalmodel.setActive(boolflag);
            repository.save(originalmodel);
        }
    }

    public void updateUserData(modelUser user, Long id){
        modelUser originalmodel = repository.findById(id).orElse(null);
        if(user != null){

            originalmodel.setSurname(user.getSurname());
            originalmodel.setName(user.getName());
            originalmodel.setPatronymic(user.getPatronymic());
            originalmodel.setEmail(user.getEmail());
            repository.save(originalmodel);
        }
    }
}
