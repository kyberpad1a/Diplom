package com.example.diplom.repo;

import com.example.diplom.model.ModelShipping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends CrudRepository<ModelShipping, Long> {

}
