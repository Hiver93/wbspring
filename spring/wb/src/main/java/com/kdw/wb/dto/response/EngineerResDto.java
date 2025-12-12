package com.kdw.wb.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

import com.kdw.wb.domain.contract.Contract;
import com.kdw.wb.domain.engineer.Engineer;

public class EngineerResDto {

	public record EngineerList(
			List<EngineerItem> list 
			) {
		public static EngineerList from(List<Engineer> engineerList){
			return new EngineerList(engineerList.stream().map(EngineerItem::from).toList());
		}
		
		private record EngineerItem(
				Integer id,
				String name,
				String type
				) {
			private static EngineerItem from(Engineer engineer) {
				return new EngineerItem(engineer.getId(), engineer.getName(), engineer.getType().getName());
			}
		}
	}
	
	public record Detail(
			Integer engineerId,
			String engineerName,
			String engineerType,
			List<String> engineerStatus,
			List<ContractItem> contractList
			) {
		public static Detail from(Engineer engineer) {
			return new Detail(engineer.getId(), engineer.getName(), engineer.getType().getName(),
					engineer.getStatusList(),
					engineer.getContractList().stream().map(ContractItem::from).toList());
		}
		
		private record ContractItem(
				Integer contractId,
				Integer salesId,
				String startDate,
				String endDate
				) {
			private static ContractItem from(Contract contract) {
				
				return new ContractItem(
						contract.getId(), 
						contract.getSales().getId(), 
						contract.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-d")),
						contract.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
			}
		}
	}
}
