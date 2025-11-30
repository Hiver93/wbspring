package com.kdw.wb.service;

import java.time.LocalDateTime;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;

public interface ContractService {

	public void createContract(Engineer engineer, Sales sales, Company company, LocalDateTime startDate, LocalDateTime endDate);
}
