package com.kdw.wb.dto.response;

import java.util.List;

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
}
