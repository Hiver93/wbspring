package com.kdw.wb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdw.wb.domain.contract.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer>{

}
