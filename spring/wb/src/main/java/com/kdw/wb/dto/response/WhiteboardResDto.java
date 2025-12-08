package com.kdw.wb.dto.response;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;

public class WhiteboardResDto {

	public record Overview(
			Summary summary,
			List<SalesItem> salesList
			) {
		
		public static Overview from(List<Engineer> engineerList, List<Sales> salesList) {
			return new Overview(Summary.from(engineerList),salesList.stream().map(SalesItem::from).toList());
		}
		private record Summary(
				String date,
				Integer currentHeadCount,
	            Integer endOfMonthHeadcount,
	            Integer endOfNextMonthHeadcount,
	              
	            Integer geCount,
	            Integer remainingEntriesThisMonth,
	            Integer remainingExitsThisMonth,

	            Integer standbyCount,
	            Integer standbyUndecidedCount,
	            Integer standbyGeCount,

	            Integer returneesThisMonth,
	            Integer shiftDecidedCount,
	            Integer shiftExtensionCount,
	            Integer transferCount,
	            Integer resignedCount,
	            Integer shiftUndecidedDecisions,

	            Integer joinThisMonth,
	            Integer decidedJoinThisMonth,
	            Integer joinNextMonth,
	            Integer shiftDecidedJoinNextMonth,

	            Integer deploymentChanges,
	            Integer weekOverWeekChange
				) {
			private static Summary from(List<Engineer> engineerList) {
				LocalDate today = LocalDate.now();
				String date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			
				// 현재인원 : 취없일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				List<Engineer> currentEngineerList = engineerList.stream().filter(e->e.isEmployed(today)).toList();
				
				// 현재인원 : 취업일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				Integer currentHeadCount = currentEngineerList.size();
				
				// 말일인원 : 취업일 정보 x, 취업일이 해당 월말과 같거나 빠름, 퇴사일이 월말보다 늦음
				List<Engineer> endOfMonthEngineerList = engineerList.stream().filter(e->e.isEmployedLastDayOf(YearMonth.from(today))).toList();
				Integer endOfMonthHeadCount = endOfMonthEngineerList.size();
				
				// 다음달말일인원 : 취업일 정보 x, 취업일이 해당 내월말과 같거나 이름, 퇴사일이 해당내월말보다 늦음
				List<Engineer> endOfNextMonthEngineerList = engineerList.stream().filter(e->e.isEmployedLastDayOf(YearMonth.from(today).plusMonths(1))).toList();
				Integer endOfNextMonthHeadCount = endOfNextMonthEngineerList.size();
				
				// 현재 이번달 GE인원 
				Integer geCount = (int)currentEngineerList.stream().filter(e->
					e.isType("GE")
				).count();
				
				// 월내입장(남은인원) : 이번달에 계약이 있고, 계약 날이 오늘 이후인 엔지니어
				Integer remainingEntriesThisMonth = (int)engineerList.stream().filter(e->
					!e.hasContract(today) && e.hasContractLastDayOf(YearMonth.from(today))
				).count();
						
				// 월내퇴장(남은인원) : 이번달에 계약이 끝나고, 계약이 끝나는 날이 오늘 이후인 엔지니어 (시프트 결정은 포함하지 않음)
				Integer remainingExitsThisMonth = (int)engineerList.stream().filter(e->
					!e.hasContract(YearMonth.from(today).plusMonths(1).atDay(1)) && e.hasContract(today)
				).count();
				
				
				// 대기자 : 계약이 없는 엔지니어, 계약이 해당일 이후인 엔지니어
				List<Engineer> standbyEngineerList = currentEngineerList.stream().filter(e->
					!e.hasContract(today)
				).toList(); 
				
	            Integer standbyCount = standbyEngineerList.size();
	            
	            List<Engineer> standbyUndecidedEngineerList = standbyEngineerList.stream().filter(e->
	            	!e.hasContractAfter(today)
	            ).toList();
	            // 계약이 없는 엔지니어
	            Integer standbyUndecidedCount = standbyUndecidedEngineerList.size();
	            
	            // 계약이 없는 엔지니어 중 GE
	            Integer standbyGeCount = (int)standbyEngineerList.stream().filter(e->
	            	e.isType("GE")
	            ).count();
	            
	            // 복사자 (계약이 이번달 끝나는 엔지니어)
	            List<Engineer> returneeList = engineerList.stream().filter(e->
	            	e.isRetuneeAt(YearMonth.from(today))
	            ).toList();
	            
	            // 복사자
	            Integer returneesThisMonth = returneeList.size();

	            // 복사자 중 계약이 잡힌 인원
	            Integer shiftDecidedCount = (int)returneeList.stream().filter(e->
	            	e.hasContractAfter(today) 
	            	|| e.hasExtensionIn(YearMonth.from(today)) 
	            ).count();
	            
	            Integer shiftExtensionCount = (int)returneeList.stream().filter(e->
	            	e.hasExtensionIn(YearMonth.from(today)) 
	            ).count();
	            // 복사자 중 타거점이동
	            Integer transferCount = 0;
	            // 복사자 중 퇴직
	            Integer resignedCount = (int)returneeList.stream().filter(e->
	            	e.getLeavingDate() != null && YearMonth.from(e.getLeavingDate()).equals(YearMonth.from(today))
	            ).count();
	            // 복사자 중 타거점인원과 퇴직과 계약 잡힌 인원을 제외
	            Integer shiftUndecidedDecisions = returneesThisMonth - shiftDecidedCount - resignedCount - transferCount;
	            
	            List<Engineer> joinThisMonthEngineerList = engineerList.stream().filter(e-> e.getJoiningDate() != null && YearMonth.from(e.getJoiningDate()).equals(YearMonth.from(today))).toList();
	            
	            // 이번달에 취업하는 엔지니어 (계약이 있기만 하면 되는 건지 이번달에 계약이 있어야하는건지)??
	            Integer joinThisMonth = joinThisMonthEngineerList.size();
	            Integer decidedJoinThisMonth = (int)joinThisMonthEngineerList.stream().filter(e->
	            		e.hasContractAfter(YearMonth.from(today).atDay(1).minusDays(1))
	            ).count();
	            
	            List<Engineer> joinNextMonthEngineerList = engineerList.stream().filter(e-> e.getJoiningDate() != null && YearMonth.from(e.getJoiningDate()).equals(YearMonth.from(today).plusMonths(1))).toList();
	            Integer joinNextMonth = joinNextMonthEngineerList.size();
	            
	            Integer shiftDecidedJoinNextMonth = (int)joinNextMonthEngineerList.stream().filter(e->
	            	e.hasContractAfter(YearMonth.from(today).atEndOfMonth())
	            ).count();

	            Integer deploymentChanges = 0;
	            Integer weekOverWeekChange = 0;
	            
				return new Summary(date, currentHeadCount, endOfMonthHeadCount, endOfNextMonthHeadCount, geCount, remainingEntriesThisMonth, remainingExitsThisMonth, standbyCount, standbyUndecidedCount, standbyGeCount, returneesThisMonth, shiftDecidedCount, shiftExtensionCount, transferCount, resignedCount, shiftUndecidedDecisions, joinThisMonth, decidedJoinThisMonth, joinNextMonth, shiftDecidedJoinNextMonth, deploymentChanges, weekOverWeekChange);
			}
		}
		
		private record SalesItem(
				Integer salesId,
				String salesName,
				List<CompanyItem> companyList
				) {
			
			private static SalesItem from(Sales sales) {
				return new SalesItem(sales.getId(), sales.getName(), sales.getContractList().stream().map(c->{
					return CompanyItem.from(c.getCompany(), sales);
				}).toList());
			}
			
			private record CompanyItem(
					Integer companyid,
					String companyName,
					List<EngineerItem> engineerList
					) {
				private static CompanyItem from(Company company, Sales sales) {
					return new CompanyItem(company.getId(), company.getName(),
							company.getContractList().stream().filter(c->c.getSales().getId().equals(sales.getId())).map(Contract::getEngineer).map(EngineerItem::from).toList());
				}
				
				private record EngineerItem(
						Integer engineerid,
						String engineerName
						) {
					private static EngineerItem from(Engineer engineer) {
						return new EngineerItem(engineer.getId(), engineer.getName());
					}
				}
			}
		}
	}
}
