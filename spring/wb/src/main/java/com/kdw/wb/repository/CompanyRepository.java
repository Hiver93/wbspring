package com.kdw.wb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
	Optional<Company> findByName(String name);
	public Set<Company> findAllByIdIn(List<Integer> idList);
}
