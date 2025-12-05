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

	private String companyNo;
	@CsvBindByName(column = "得意先名")
	private String companyName;
	private String engineerNo;
	@CsvBindByName(column = "ｴﾝｼﾞﾆｱ名")
	private String engineerName;
	private String salesNo;
	@CsvBindByName(column = "営業担当者名")
	private String salesName;
	@CsvBindByName(column = "開始請求期間")
	private String startDate;
	@CsvBindByName(column = "終了請求期間")
	private String endDate;
	private String type;
	private String companyHouse;
	private String takeover;
	@Override
	public String toString() {
		return "ContractInfo [companyNo=" + companyNo + ", companyName=" + companyName + ", engineerNo=" + engineerNo
				+ ", engineerName=" + engineerName + ", salesNo=" + salesNo + ", salesName=" + salesName
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", type=" + type + ", companyHouse="
				+ companyHouse + ", takeover=" + takeover + "]";
	}
	
	
}
//  得意先名	ｴﾝｼﾞﾆｱ名	営業担当者名	開始請求期間	終了請求期間
