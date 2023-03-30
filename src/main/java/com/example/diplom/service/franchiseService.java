package com.example.diplom.service;

import com.example.diplom.model.modelCategory;
import com.example.diplom.model.modelFranchise;
import com.example.diplom.repo.CategoryRepository;
import com.example.diplom.repo.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class franchiseService {
    @Autowired
    private FranchiseRepository repository;
    public void addFranchise(modelFranchise franchise){
        repository.save(franchise);
    }
    public void updateFranchise(modelFranchise franchise, Long id){
        modelFranchise originalmodel = repository.findById(id).orElse(null);
        if(franchise != null){

            originalmodel.setFranchise_Name(franchise.getFranchise_Name());
            repository.save(originalmodel);
        }
    }
}
