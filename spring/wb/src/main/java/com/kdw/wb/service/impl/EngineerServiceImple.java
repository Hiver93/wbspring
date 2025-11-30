package com.kdw.wb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerStatus;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.Sales;
import com.kdw.wb.repository.EngineerRepository;
import com.kdw.wb.service.EngineerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EngineerServiceImple implements EngineerService{
	
	private final EngineerRepository engineerRepository;
	
	@Override
	public void createEngineer(Integer no, String name, EngineerStatus status, EngineerType engineerType) {
		Engineer engineer = Engineer.builder().no(no).name(name).status(status).type(engineerType).build();
		this.engineerRepository.save(engineer);
	}

	@Override
	public void modifyEngineer(Integer engineerId, String name, Company company, Sales sales) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Engineer getEngineer(Integer engineerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Engineer> getEngineerListByCompany() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Engineer> getEngineerListBySales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEngineer(Integer engineerId) {
		// TODO Auto-generated method stub
		
	}

}
