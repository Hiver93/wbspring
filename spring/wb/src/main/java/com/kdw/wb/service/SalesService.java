package com.kdw.wb.service;

import java.util.List;

import com.kdw.wb.domain.Sales;

public interface SalesService {

	public void createSales(String name);
	public Sales getSales(Integer salesId);
	public List<Sales> getSalesList();
	public void removeSales(Integer salesId);
	public void modifySales(Integer salesId, String name);
}
