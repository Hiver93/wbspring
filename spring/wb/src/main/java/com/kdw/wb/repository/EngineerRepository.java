package com.kdw.wb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.engineer.Engineer;

public interface EngineerRepository extends JpaRepository<Engineer, Integer>{
	public Optional<Engineer> findByNo(Integer no);
	public boolean existsByNo(Integer no);
	public Set<Engineer> findAllByNoIn(List<Integer> noList);
}
