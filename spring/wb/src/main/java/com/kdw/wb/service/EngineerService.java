package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.Sales;

public interface EngineerService {

	public Engineer createEngineer(Integer no, String name, EngineerType engineerType, Boolean companyHouse);
	public void modifyEngineer(Integer engineerId, String name, Company company, Sales sales);
	public Engineer getEngineer(Integer engineerId);
	public List<Engineer> getEngineerListByCompany();
	public List<Engineer> getEngineerListBySales();
	public List<Engineer> getEngineerList();
	public void removeEngineer(Integer engineerId);
	public EngineerType getEngineerType(String typeName);
	public Engineer getEngineerByNo(Integer engineerNo);
}
