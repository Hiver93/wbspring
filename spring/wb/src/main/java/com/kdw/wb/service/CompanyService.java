package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.ContractInfo;

public interface CompanyService {
	public void createCompany(Integer no, String name);
	public Company getCompany(Integer companyId);
	public List<Company> getCompanyList();
	public void modifyCompany(Integer companyId, String name);
	public void removeCompany(Integer companyid);
	public Company getCompany(String name);
	public Company getCompanyByNo(Integer companyNo);
	public void ensureCompanies(List<ContractInfo> contractInfoList);
}
