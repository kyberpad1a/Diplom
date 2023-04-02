package com.example.diplom.service;

import com.example.diplom.model.modelFranchise;
import com.example.diplom.model.modelGood;
import com.example.diplom.repo.FranchiseRepository;
import com.example.diplom.repo.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class goodService {
    @Autowired
    private GoodRepository repository;
    public void addGood(modelGood good){
        repository.save(good);
    }
    public void updateGood(modelGood good, Long id){
        modelGood originalmodel = repository.findById(id).orElse(null);
        if(good != null){

            originalmodel.setGood_Name(good.getGood_Name());
            originalmodel.setGood_Description(good.getGood_Description());

            repository.save(originalmodel);
        }
    }
}
