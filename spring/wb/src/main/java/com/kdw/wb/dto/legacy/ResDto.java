package com.kdw.wb.dto.legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;

public class ResDto {
	public static record OverviewItem(
			String username,
			List<EngineerItem> engineers,
			Map<String,List<Integer>> engineer_group_by_status,
			String first_name,
			String last_name,
			List<CompanyItem> companies
			) {		
		
		public static OverviewItem from(Sales sales) {
			Map<String,List<Integer>> map = new HashMap<>();
			sales.getContractList().stream().forEach(c->{
						Engineer e = c.getEngineer();
						if(!map.containsKey(e.getStatus().getName())) {
							map.put(e.getStatus().getName(), new ArrayList<Integer>());
						}
						map.get(e.getStatus().getName()).add(e.getId());
					});
			return new OverviewItem(
					sales.getName(),
					sales.getContractList().stream().map(EngineerItem::from).toList(),
					map,
					null,
					null,
					sales.getContractList().stream()
					.map(Contract::getCompany).distinct()
					.map(CompanyItem::from).toList()					
					);
		}
		private static record EngineerItem(
				Integer id,
				String engineer_name,
				Integer sales_id,
				String engineer_status,
				Integer company_id
				) {
			private static EngineerItem from(Contract contract) {
				return new EngineerItem(contract.getEngineer().getId(), contract.getEngineer().getName(), contract.getSales().getId(), contract.getEngineer().getStatus().getName(), contract.getCompany().getId());				
			}
		}  
		
		private static record CompanyItem(
				Integer id,
				String company_name,
				List<Integer> engineers
				) {
			private static CompanyItem from(Company company) {
				return new CompanyItem(company.getId(), company.getName(), 
						company.getContractList().stream().map(c->
						c.getEngineer().getId()
						).toList()
						);
			}
		}
	}
}
