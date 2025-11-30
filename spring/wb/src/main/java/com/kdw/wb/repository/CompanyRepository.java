package com.kdw.wb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

}
