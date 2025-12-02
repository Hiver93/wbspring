package com.kdw.wb.dto.response;

import java.util.List;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.engineer.EngineerJoining;
import com.kdw.wb.domain.engineer.EngineerRetunee;
import com.kdw.wb.domain.sales.Sales;

public class WhiteboardResDto {

	public record Overview(
			Summary summary,
			List<SalesItem> sales_list
			) {
		
		public Overview from(List<Sales> salesList, List<Engineer> engineerList) {
			
			List<SalesItem> salesItemList = salesList.stream().map(SalesItem::from).toList();
			
			return null;
		}
		
		private record Summary(
				Integer current_headcount,
				Integer end_of_month_headcount,
				Integer end_of_next_month_headcount,
				
				Integer retunees_count,
				
				String date,
				Integer ge_count,
				Integer remaining_entries_this_month,
				Integer remaining_exits_this_month,
				
				Integer standby_count,
				Integer standby_undecided_count,
				Integer standby_ge_count,
				
				Integer retunees_this_month,
				Integer shift_decided_retunees,
				Integer shift_extension_count,
				Integer resigned_count,
				Integer shift_undecided_retunees,
				
				Integer join_this_month,
				Integer shift_decided_join_this_month,
				Integer join_next_month,
				Integer shift_decided_join_next_month,
				
				Integer deployment_changes,
				Integer week_over_week_change
				) {
//			private Summary from(List<Engineer> engineerList) {
//				Integer current_headcount = (int) engineerList.stream().filter(e->e.getJoining().getType() == null).count();
//				Integer end_of_month_headcount = (int) engineerList.stream().filter(e->!e.getJoining().getType().equals(EngineerJoining.Type.NEXT_MONTH.getName())).count();
//				Integer end_of_next_month_headcount = (int) engineerList.stream().filter(e->{
//								String returneeTypeName = e.getReturnee().getType();
//								if(returneeTypeName.equals(EngineerRetunee.Type.LEAVING.getName())
//										|| returneeTypeName.equals(EngineerRetunee.Type.MOVING.getName())
//										|| returneeTypeName.equals(EngineerRetunee.Type.RESIGNATION.getName())) {
//									return false;
//								}
//								return true;
//							})
//						.count();
//				
//				
//			}
		}
		
		private record SalesItem(
				Integer sales_id,
				String name,
				List<CompanyItem>  companies
				) {
			
			private static SalesItem from(Sales sales) {
				return new SalesItem(sales.getId(), sales.getName(), 
						sales.getContractList().stream().map(Contract::getCompany).map(CompanyItem::from).toList());
			}
			
			private record CompanyItem(
					Integer company_id,
					List<EngineerItem> engineers
					) {
				private static CompanyItem from(Company company) {
					return new CompanyItem(company.getId(), 
							company.getContractList().stream().map(Contract::getEngineer)
							.map(EngineerItem::from).toList());
				}
				
				private record EngineerItem(
						Integer engineer_id,
						String name
						) {
					private static EngineerItem from(Engineer engineer) {
						return new EngineerItem(engineer.getId(), engineer.getName());
					}
				}
			}
		}
	}
}