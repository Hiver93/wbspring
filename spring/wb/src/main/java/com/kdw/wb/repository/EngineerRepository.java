package com.kdw.wb.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.engineer.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Integer>{
	public Set<Engineer> findAllByIdIn(List<Integer> idList);
	public List<Engineer> findAllByNameContaining(String name);
}
