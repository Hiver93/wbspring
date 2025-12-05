package com.kdw.wb.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.repository.ContractRepository;
import com.kdw.wb.service.ContractService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

	private final ContractRepository contractRepository;

	@Override
	public void createContract(Engineer engineer, Sales sales, Company company, LocalDateTime startDate,
			LocalDateTime endDate, boolean selfContracting) {
		Contract contract = Contract.builder().engineer(engineer).sales(sales).company(company).startDate(startDate).endDate(endDate).selfContracting(sales).build();
		this.contractRepository.save(contract);
	}
}
