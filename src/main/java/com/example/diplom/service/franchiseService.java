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

    /**
     * Метод для добавления франшизы в репозиторий франшиз
     * @param franchise модель франшизы, которую нужно добавить
     */
    public void addFranchise(modelFranchise franchise){
        repository.save(franchise);
    }

    /**
     * Метод для обновления франшизы по идентификатору
     * @param franchise модель франшизы, которую нужно обновить
     * @param id идентификатор франшизы, которую нужно обновить
     */
    public void updateFranchise(modelFranchise franchise, Long id){
        modelFranchise originalmodel = repository.findById(id).orElse(null);
        if(franchise != null){

            originalmodel.setFranchise_Name(franchise.getFranchise_Name());
            repository.save(originalmodel);
        }
    }
}
