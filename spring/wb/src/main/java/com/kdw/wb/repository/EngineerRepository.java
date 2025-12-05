package com.kdw.wb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.engineer.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Integer>{
	public Optional<Engineer> findByNo(Integer no);
}
