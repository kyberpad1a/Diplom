package com.example.diplom.service;

import com.example.diplom.model.modelCategory;
import com.example.diplom.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryService {
    @Autowired
    private CategoryRepository repository;
public void addCategory(modelCategory category){
    repository.save(category);
}
public void updateCategory(modelCategory category, Long id){
    modelCategory originalmodel = repository.findById(id).orElse(null);
    if(category != null){

        originalmodel.setCategory_Name(category.getCategory_Name());
        repository.save(originalmodel);
    }
}

}
