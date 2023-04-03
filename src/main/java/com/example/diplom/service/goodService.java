package com.example.diplom.service;

import com.example.diplom.model.modelGood;
import com.example.diplom.model.modelPhoto;
import com.example.diplom.repo.GoodRepository;
import com.example.diplom.repo.PhotoRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class goodService {
    @Autowired
    private GoodRepository repository;

    @Autowired
    private PhotoRepository photoRepository;
    public void addGood(modelGood good, List<byte[]> bytes){

        repository.save(good);
        for (byte[] byt:bytes) {
            modelPhoto modelPhoto = new modelPhoto();
            modelPhoto.setPhoto_Path(byt);
            modelPhoto.setGood(good);
            photoRepository.save(modelPhoto);
        }





    }
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
