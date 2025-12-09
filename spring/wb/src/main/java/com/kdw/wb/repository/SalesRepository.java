package com.kdw.wb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdw.wb.domain.sales.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {
	
	public Optional<Sales> findByName(String name);
	public Set<Sales> findAllByIdIn(List<Integer> idList);
}
