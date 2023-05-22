package com.example.diplom.repo;

import com.example.diplom.model.ModelShipping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс репозитория для операций с объектами модели доставки
 */
@Repository
public interface ShippingRepository extends CrudRepository<ModelShipping, Long> {

}
