package com.kdw.wb.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.repository.ContractRepository;
import com.kdw.wb.service.CompanyService;
import com.kdw.wb.service.ContractService;
import com.kdw.wb.service.EngineerService;
import com.kdw.wb.service.SalesService;
import com.kdw.wb.util.TimeFormatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

	private final ContractRepository contractRepository;
	private final EngineerService engineerService;
	private final SalesService salesService;
	private final CompanyService companyService;

	@Override
	public void createContract(Engineer engineer, Sales sales, Company company, LocalDateTime startDate,
			LocalDateTime endDate, boolean selfContracting) {
		Contract contract = Contract.builder().engineer(engineer).sales(sales).company(company).startDate(startDate).endDate(endDate).selfContracting(sales).build();
		this.contractRepository.save(contract);
	}

	@Override
	public void ensureContracts(List<ContractInfo> contractInfoList) {
		List<Contract> savedContractList = this.contractRepository.findAll();
		Set<String> savedContractKeys = savedContractList.stream()
				.map(c -> new StringBuilder()
						.append(c.getCompany().getId()).append('/')
						.append(c.getEngineer().getId()).append('/')
						.append(c.getSales().getId()).append('/')
						.append(c.getStartDate().toString()).append('/')
						.append(c.getEndDate().toString())
						.toString())
				.collect(Collectors.toSet());
		
		List<Contract> contractList = contractInfoList.stream().filter(c->!savedContractKeys.contains(
				new StringBuilder()
				.append(c.getCompanyId()).append('/')
				.append(c.getEngineerId()).append('/')
				.append(c.getSalesId()).append('/')
				.append(TimeFormatUtil.convert(c.getStartDate()).toString()).append('/')
				.append(TimeFormatUtil.convert(c.getEndDate()).toString())
				.toString()
				))
		.map(c->{
			Sales sales = this.salesService.getSales(Integer.valueOf(c.getSalesId()));
			return Contract.builder()
				.company(this.companyService.getCompany(Integer.valueOf(c.getCompanyId())))
				.engineer(this.engineerService.getEngineer(Integer.valueOf(c.getEngineerId())))
				.sales(sales)
				.startDate(TimeFormatUtil.convert(c.getStartDate()))
				.endDate(TimeFormatUtil.convert(c.getEndDate()))
				.selfContracting(c.getTakeover().equals("0") ? null : sales)
				.build();}).toList();
		
		this.contractRepository.saveAll(contractList);
	}
}
