package com.kdw.wb.dto.legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kdw.wb.domain.Company;
import com.kdw.wb.domain.Engineer;
import com.kdw.wb.domain.Sales;

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
			sales.getEngineerList().stream().forEach(e->{
						if(!map.containsKey(e.getStatus())) {
							map.put(e.getStatus(), new ArrayList<Integer>());
						}
						map.get(e.getStatus()).add(e.getId());
					});
			return new OverviewItem(
					sales.getName(),
					sales.getEngineerList().stream().map(EngineerItem::from).toList(),
					map,
					null,
					null,
					sales.getCompanyList().stream().map(CompanyItem::from).toList()					
					);
		}
		private static record EngineerItem(
				Integer id,
				String engineer_name,
				Integer sales_id,
				String engineer_status,
				Integer copany_id
				) {
			private static EngineerItem from(Engineer engineer) {
				return new EngineerItem(engineer.getId(), engineer.getName(), engineer.getSales().getId(), engineer.getStatus(), engineer.getCompany().getId());				
			}
		}  
		
		private static record CompanyItem(
				Integer id,
				String company_name,
				List<Integer> engineers
				) {
			private static CompanyItem from(Company company) {
				return new CompanyItem(company.getId(), company.getName(), 
						company.getEngineerList().stream().map(e->e.getId()).toList()
						);
			}
		}
	}
}
