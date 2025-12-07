package com.kdw.wb.domain.engineer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdw.wb.domain.contract.Contract;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Engineer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private Integer no;
	@Column
	private String name;
	@Column
	private Boolean companyHouse;
	@ManyToOne
	private EngineerType type;
	@Column
	private LocalDateTime joiningDate;
	@Column
	private LocalDateTime leavingDate;
	@OneToMany(mappedBy = "engineer")
	private List<Contract> contractList = new ArrayList<>();
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Builder
	public Engineer(Integer no, String name, EngineerType type, Boolean companyHouse) {
		super();
		this.no = no;
		this.name = name;
		this.type = type;
		this.companyHouse = companyHouse;
	}

	
	// 해당월말에 유지되는 계약이 있는지?
	public boolean hasContractLastDayOf(YearMonth month) {
		
		return this.getContractList().stream().anyMatch(c->{
			YearMonth startMonth = YearMonth.from(c.getStartDate());
			YearMonth endMonth = YearMonth.from(c.getEndDate());
			return (startMonth.equals(month) && !endMonth.equals(month))
					|| (startMonth.isBefore(month) && endMonth.isAfter(month));
		});
	}
	
	// 해당일에 유지되는 계약이 있는지?
	public boolean hasContract(LocalDate date) {
		return this.getContractList().stream().anyMatch(c->{
			LocalDate startDate = c.getStartDate().toLocalDate();
			LocalDate endDate = c.getEndDate().toLocalDate();
			return (startDate.equals(date))
					|| (startDate.isBefore(date) && endDate.isAfter(date));
		});
	}
	
	// 해당월말에 회사에 고용된 상태인가
	public boolean isEmployedLastDayOf(YearMonth month) {
		return this.getLeavingDate() == null 
				|| YearMonth.from(this.getLeavingDate()).isAfter(month);
	}
	
	// 해당일에 회사에 고용된 상태인가
	public boolean isEmployed(LocalDate date) {
		return this.getLeavingDate() == null
				|| this.getLeavingDate().toLocalDate().isAfter(date);
	}
}
