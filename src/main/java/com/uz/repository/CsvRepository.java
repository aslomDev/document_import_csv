package com.uz.repository;

import com.uz.entity.CsvEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsvRepository extends JpaRepository<CsvEntity, Integer> {

    List<CsvEntity> findByOrderByIdDesc();




}
