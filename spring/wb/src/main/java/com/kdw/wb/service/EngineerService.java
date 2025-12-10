package com.kdw.wb.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.ContractInfo;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerType;
import com.kdw.wb.domain.sales.Sales;

public interface EngineerService {

	public Engineer createEngineer(Integer id, String name, EngineerType engineerType, Boolean companyHouse);
	public Engineer getEngineer(Integer engineerId);
	public List<Engineer> getEngineerList();
	public EngineerType getEngineerType(String typeName);
	public void ensureEngineers(List<ContractInfo> engineerList);
	public void updateReturnees(Map<Integer, String> returneesMap, LocalDateTime localDateTime);
	public List<Engineer> searchEngineer(String keyword, String target);
}
