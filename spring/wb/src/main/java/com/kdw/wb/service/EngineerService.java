package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.Company;
import com.kdw.wb.domain.Engineer;
import com.kdw.wb.domain.Sales;

public interface EngineerService {

	public void createEngineer(Integer no, String name, Company company, Sales sales);
	public void modifyEngineer(Integer engineerId, Integer no, String name, Company company, Sales sales);
	public Engineer getEngineer(Integer engineerId);
	public List<Engineer> getEngineerListByCompany();
	public List<Engineer> getEngineerListBySales();
	public void removeEngineer(Integer engineerId);
}
