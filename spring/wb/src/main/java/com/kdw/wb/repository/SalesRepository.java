package com.kdw.wb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kdw.wb.domain.sales.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Integer> {

}
