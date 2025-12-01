package com.kdw.wb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
	Optional<Company> findByName(String name);
}
