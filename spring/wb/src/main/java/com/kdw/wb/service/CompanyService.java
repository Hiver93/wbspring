package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.company.Company;

public interface CompanyService {
	public void createCompany(String name);
	public Company getCompany(Integer companyId);
	public List<Company> getCompanyList();
	public void modifyCompany(Integer companyId, String name);
	public void removeCompany(Integer companyid);
	public Company getCompany(String name);
}
