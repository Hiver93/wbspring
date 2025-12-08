package com.kdw.wb.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.error.ErrorCode;
import com.kdw.wb.error.WhiteboardException;
import com.kdw.wb.repository.CompanyRepository;
import com.kdw.wb.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository companyRepository;
	
	@Override
	public void createCompany(Integer no, String name) {
		Company company = Company.builder().no(no).name(name).build();
		this.companyRepository.save(company);
	}

	@Override
	public Company getCompany(Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> getCompanyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyCompany(Integer companyId, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeCompany(Integer companyid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Company getCompany(String name) {
		try {
		return this.companyRepository.findByName(name).orElseThrow(()->{
			System.out.println(name);
			throw new WhiteboardException(ErrorCode.COMPANY_NOT_FOUND);
			});
		}catch(Exception e) {
			System.out.println(name);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Company getCompanyByNo(Integer companyNo) {
		return this.companyRepository.findByNo(companyNo).orElseThrow(()->{
			System.out.println(companyNo);
			throw new WhiteboardException(ErrorCode.COMPANY_NOT_FOUND);
			});
	}

	@Override
	public void ensureCompanies(List<ContractInfo> contractInfoList) {
		List<Integer> noList = contractInfoList.stream().map(i->Integer.valueOf(i.getCompanyNo())).toList();
		
		Set<Integer> noSet = this.companyRepository.findAllByNoIn(noList).stream().map(Company::getNo).collect(Collectors.toSet());
		
		List<Company> companyList =
		contractInfoList.stream()
			.filter(i-> !noSet.contains(Integer.valueOf(i.getCompanyNo())))
			.map(info -> Company.builder()
					.no(Integer.valueOf(info.getCompanyNo()))
					.name(info.getCompanyName())
					.build())
			.collect(Collectors.toMap(
			        Company::getNo,  
			        user -> user,          
			        (oldValue, newValue) -> oldValue 
			    ))
			.values().stream()
			.toList();
		
		this.companyRepository.saveAll(companyList);
	}

}
