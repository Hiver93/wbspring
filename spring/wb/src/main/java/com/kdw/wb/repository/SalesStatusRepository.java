package com.kdw.wb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdw.wb.domain.sales.SalesStatus;

@Repository
public interface SalesStatusRepository extends JpaRepository<SalesStatus, Integer>{

	public Optional<SalesStatus> findByname(String name);
}
