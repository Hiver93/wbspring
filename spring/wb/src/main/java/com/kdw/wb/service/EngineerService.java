package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerStatus;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.Sales;

public interface EngineerService {

	public void createEngineer(Integer no, String name, EngineerStatus status, EngineerType engineerType);
	public void modifyEngineer(Integer engineerId, String name, Company company, Sales sales);
	public Engineer getEngineer(Integer engineerId);
	public List<Engineer> getEngineerListByCompany();
	public List<Engineer> getEngineerListBySales();
	public void removeEngineer(Integer engineerId);
}
