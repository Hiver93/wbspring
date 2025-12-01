package com.kdw.wb.domain.contract;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Builder
public class ContractInfo {

	@CsvBindByName(column = "得意先名")
	private String companyName;
	@CsvBindByName(column = "ｴﾝｼﾞﾆｱ名")
	private String engineerName;
	@CsvBindByName(column = "営業担当者名")
	private String salesName;
	@CsvBindByName(column = "開始請求期間")
	private String startDate;
	@CsvBindByName(column = "終了請求期間")
	private String endDate;
	@Override
	public String toString() {
		return "ContractInfo [companyName=" + companyName + ", engineerName=" + engineerName + ", salesName="
				+ salesName + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
//  得意先名	ｴﾝｼﾞﾆｱ名	営業担当者名	開始請求期間	終了請求期間
