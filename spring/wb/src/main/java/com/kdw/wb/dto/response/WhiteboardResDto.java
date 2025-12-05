package com.kdw.wb.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
				LocalDateTime today = LocalDateTime.now();
				LocalDate localDate = today.toLocalDate();
				LocalDate lastDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
				String date = "2025-12-05";
			
				// 현재인원 : 취없일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				List<Engineer> currentEngineerList = engineerList.stream().filter(e->{
					LocalDateTime joiningDate = e.getJoiningDate();
					if(joiningDate == null)
						return true;
					if(joiningDate.toLocalDate().isEqual(localDate) || joiningDate.toLocalDate().isBefore(localDate)) {
						return true;
					}
					if(joiningDate.toLocalDate().isAfter(localDate)) {
						return true;
					}
					return false;
				}).toList();
				
				// 현재인원 : 취없일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				Integer currentHeadCount = currentEngineerList.size();
				
				// 말일인원 : 취없일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				Integer endOfMonthHeadCount = (int)engineerList.stream().filter(e->{
					LocalDateTime joiningDate = e.getJoiningDate();
					if(joiningDate == null)
						return true;
					if(joiningDate.toLocalDate().isEqual(lastDay.plusMonths(1)) || joiningDate.toLocalDate().isBefore(lastDay)) {
						return true;
					}
					if(joiningDate.toLocalDate().isAfter(lastDay)) {
						return true;
					}
					return false;
				}).count();
				
				// 다음달말일인원 : 취없일 정보 x, 취업일이 해당 날과 같거나 빠름, 퇴사일이 해당일보다 늦음
				Integer endOfNextMonthHeadCount = (int)engineerList.stream().filter(e->{
					LocalDateTime joiningDate = e.getJoiningDate();
					LocalDate nextLastDay = lastDay.plusMonths(1);
					if(joiningDate == null)
						return true;
					if(joiningDate.toLocalDate().isEqual(nextLastDay) || joiningDate.toLocalDate().isBefore(nextLastDay)) {
						return true;
					}
					if(joiningDate.toLocalDate().isAfter(nextLastDay)) {
						return true;
					}
					return false;
				}).count();
				
				// 이번달 인원 
				Integer geCount = (int)currentEngineerList.stream().filter(e->{
					if(e.getType().getName().equals("GE")) {
						return true;
					}
					return false;
				}).count();
				
				// 월내입장 : 이번달에 계약이 있고, 계약 날이 오늘 이후인 엔지니어
				Integer remainingEntriesThisMonth = (int)engineerList.stream().filter(e->{
					if(e.getContract() != null) {
						if(e.getContract().getStartDate().getMonth().equals(today.getMonth()) && e.getContract().getStartDate().isAfter(today)) {
							return true;
						}
					}
					return false;
				}).count();
				
				// 월내퇴장 : 이번달에 계약이 끝나고, 계약이 끝나는 날이 오늘 이후인 엔지니어
				Integer remainingExitsThisMonth = (int)engineerList.stream().filter(e->{
					if(e.getContract() != null) {
						if(e.getContract().getEndDate().getMonth().equals(today.getMonth()) &&  e.getContract().getStartDate().isAfter(today)) {
							return true;
						}
					}
					return false;
				}).count();
				
				
				// 대기자 : 계약이 없는 엔지니어, 계약이 해당일 이후인 엔지니어
				List<Engineer> standbyEngineer = currentEngineerList.stream().filter(e->{
	            	if(e.getContract() == null || e.getContract().getStartDate().isAfter(today)) {
	            		return true;
	            	}
	            	return false;
	            }).toList(); 
				
	            Integer standbyCount = standbyEngineer.size();
	            Integer standbyUndecidedCount = (int)standbyEngineer.stream().filter(e->{
	            	if(e.getContract() == null) {
	            		return true;
	            	}
	            	return false;
	            }).count();
	            Integer standbyGeCount = (int)standbyEngineer.stream().filter(e->{
	            	if(e.getContract() == null && e.getType().getName().equals("GE")) {
	            		return true;
	            	}
	            	return false;
	            }).count();
	            
	            // 복사자
	            List<Engineer> returneeList = engineerList.stream().filter(e->{
	            	if(e.getContract() != null) {
	            		if(e.getContract().getEndDate().getMonth().equals(today.getMonth())) {
	            			return true;
	            		}
	            	}
	            	return false;
	            }).toList();
	            
	            // 복사자
	            Integer returneesThisMonth = returneeList.size();

	            // 복사자 중 계약이 잡힌 인원
	            Integer shiftDecidedCount = (int)returneeList.stream().filter(e->{
	            	if(!e.getContract().getExtension()) {
	            		return true;
	            	}
	            	return false;
	            }).count();
	            Integer shiftExtensionCount = 0;
	            // 복사자 중 타거점이동
	            Integer transferCount = 0;
	            // 복사자 중 퇴직
	            Integer resignedCount = (int)returneeList.stream().filter(e->{
	            	if(e.getLeavingDate().getMonth().equals(today.getMonth())) {
	            		return true;
	            	}
	            	return false;
	            }).count();
	            // 복사자 중 타거점인원과 퇴직과 계약 잡힌 인원을 제외
	            Integer shiftUndecidedDecisions = returneesThisMonth - shiftDecidedCount - resignedCount - transferCount;
	            
	            
	            Integer joinThisMonth = (int)engineerList.stream().filter(e->{
	            	if(e.getJoiningDate() != null) {
	            		if(e.getJoiningDate().getMonth().equals(today.getMonth())) {
	            			return true;
	            		}
	            	}
					return false;
	            }).count();
	            Integer decidedJoinThisMonth = (int)engineerList.stream().filter(e->{
	            	if(e.getJoiningDate() != null) {
	            		if(e.getJoiningDate().getMonth().equals(today.getMonth())) {
	            			if(e.getContract()!= null && e.getContract().getStartDate().getMonth().equals(today.getMonth())) {
	            				return true;
	            			}
	            		}
	            	}
	            	return false;
	            }).count();
	            Integer joinNextMonth = (int)engineerList.stream().filter(e->{
	            	if(e.getJoiningDate() != null) {
	            		if(e.getJoiningDate().getMonth().equals(today.getMonth().plus(1))) {
	            			return true;
	            		}
	            	}
					return false;
	            }).count();
	            Integer shiftDecidedJoinNextMonth = (int)engineerList.stream().filter(e->{
	            	if(e.getJoiningDate() != null) {
	            		if(e.getJoiningDate().getMonth().equals(today.getMonth())) {
	            			if(e.getContract()!= null && e.getContract().getStartDate().getMonth().equals(today.getMonth())) {
	            				return true;
	            			}
	            		}
	            	}
					return false;
	            }).count();

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
