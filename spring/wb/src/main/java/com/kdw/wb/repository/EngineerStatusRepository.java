package com.kdw.wb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdw.wb.domain.engineer.EngineerStatus;

@Repository
public interface EngineerStatusRepository extends JpaRepository<EngineerStatus, Integer>{

	public Optional<EngineerStatus> findByName(String name);
}
