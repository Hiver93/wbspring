package com.kdw.wb.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.ContractInfo;
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
	public void createCompany(Integer id, String name) {
		Company company = Company.builder().id(id).name(name).build();
		this.companyRepository.save(company);
	}

	@Override
	public Company getCompany(Integer companyId) {
		return this.companyRepository.findById(companyId).orElseThrow(()->{throw new WhiteboardException(ErrorCode.COMPANY_NOT_FOUND, "id : "+ companyId.toString());});
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
	public void ensureCompanies(List<ContractInfo> contractInfoList) {
		List<Integer> idList = contractInfoList.stream().map(i->Integer.valueOf(i.getCompanyId())).toList();
		
		Set<Integer> idSet = this.companyRepository.findAllByIdIn(idList).stream().map(Company::getId).collect(Collectors.toSet());
		
		List<Company> companyList =
		contractInfoList.stream()
			.filter(i-> !idSet.contains(Integer.valueOf(i.getCompanyId())))
			.map(info -> Company.builder()
					.id(Integer.valueOf(info.getCompanyId()))
					.name(info.getCompanyName())
					.build())
			.collect(Collectors.toMap(
			        Company::getId,  
			        user -> user,          
			        (oldValue, newValue) -> oldValue 
			    ))
			.values().stream()
			.toList();
		
		this.companyRepository.saveAll(companyList);
	}

}
