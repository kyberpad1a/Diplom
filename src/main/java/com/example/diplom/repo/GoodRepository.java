package com.example.diplom.repo;

import com.example.diplom.model.modelGood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.PageRequest;
import java.util.Collection;
import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<modelGood, Long> {
    public List<modelGood> findAllByLogicalFlagFalse(PageRequest pageRequest);
    public Collection<modelGood> findAllByLogicalFlagFalse();
    public modelGood findByIDGood(Long id);
    public int countAllByLogicalFlagFalse();
}
