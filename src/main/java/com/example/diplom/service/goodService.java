package com.example.diplom.service;

import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelPhoto;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.PhotoRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс, предоставляющий методы для работы с моделью товара.
 */
@Service
public class goodService {

    /**
     * Экземпляр репозитория для доступа к данным о товарах.
     */
    @Autowired
    private GoodRepository repository;

    /**
     * Экземпляр репозитория для доступа к данным о фотографиях.
     */
    @Autowired
    private PhotoRepository photoRepository;

    /**
     * Метод добавления нового товара в БД с фотографиями товара.
     * @param good Объект модели товара, который нужно добавить.
     * @param bytes Список массивов байт, содержащих изображения товара.
     */
    public void addGood(modelGood good, List<byte[]> bytes){
        repository.save(good);
        for (byte[] byt:bytes) {
            modelPhoto modelPhoto = new modelPhoto();
            modelPhoto.setPhoto_Path(byt);
            modelPhoto.setGood(good);
            photoRepository.save(modelPhoto);
        }
    }

    /**
     * Метод обновления информации о товаре по его id.
     * @param good Объект модели товара, содержащий новые данные для обновления.
     * @param id Идентификатор товара, который нужно обновить.
     */
    public void updateGood(modelGood good, Long id){
        modelGood originalmodel = repository.findById(id).orElse(null);
        if(good != null){
            originalmodel.setGood_Name(good.getGood_Name());
            originalmodel.setGood_Material(good.getGood_Material());
            originalmodel.setGood_Price(good.getGood_Price());
            originalmodel.setGood_Description(good.getGood_Description());
            originalmodel.setCategory(good.getCategory());
            originalmodel.setFranchise(good.getFranchise());
            originalmodel.setLogicalFlag(false);
            repository.save(originalmodel);
        }
    }

    /**
     * Метод помечает товар в БД как удаленный логически.
     * @param good Объект модели товара, который нужно пометить как удаленный.
     * @param id Идентификатор товара, который нужно пометить как удаленный.
     */
    public void logicalDelete(modelGood good, Long id){
        modelGood originalmodel = repository.findById(id).orElse(null);
        if(good != null) {
            originalmodel.setGood_Name(good.getGood_Name());
            originalmodel.setGood_Material(good.getGood_Material());
            originalmodel.setGood_Price(good.getGood_Price());
            originalmodel.setGood_Description(good.getGood_Description());
            originalmodel.setCategory(good.getCategory());
            originalmodel.setFranchise(good.getFranchise());
            originalmodel.setLogicalFlag(true);
            repository.save(originalmodel);
        }
    }
}
