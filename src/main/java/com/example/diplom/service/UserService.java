package com.example.diplom.service;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelUser;
import com.example.diplom.model.roleEnum;
import com.example.diplom.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Сервис-класс для работы с пользователями
 */
@Service
public class UserService {

    /**
     * Репозиторий для доступа к данным пользователей
     */
    @Autowired
    private UserRepository repository;

    /**
     * Метод для обновления роли пользователя.
     *
     * @param user - объект modelUser, содержащий данные пользователя
     * @param id - идентификатор пользователя
     * @param flag - строковый флаг роли ("ADMIN", "USER", "COURIER" или "GOODSSTAFF")
     */
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

    /**
     * Метод для обновления статуса активности пользователя.
     *
     * @param user - объект modelUser, содержащий данные пользователя
     * @param id - идентификатор пользователя
     * @param boolflag - булево значение статуса активности пользователя
     */
    public void updateUserActive(modelUser user, Long id, boolean boolflag){
        modelUser originalmodel = repository.findById(id).orElse(null);
        if(user != null){
            originalmodel.setActive(boolflag);
            repository.save(originalmodel);
        }
    }

    /**
     * Метод для обновления данных пользователя.
     *
     * @param user - объект modelUser, содержащий данные пользователя
     * @param id - идентификатор пользователя
     */
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

