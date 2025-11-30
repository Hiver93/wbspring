package com.kdw.wb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.engineer.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Integer>{

}
