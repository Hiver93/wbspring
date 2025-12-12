package com.kdw.wb.domain.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;
import com.kdw.wb.domain.sales.Sales;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Contract  implements Persistable<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Sales sales;
	@ManyToOne
	private Sales selfContracting;
	@ManyToOne
	private Engineer engineer;
	@ManyToOne
	private Company company;
	@Column
	private Boolean extension = true;
	@Column
	private LocalDateTime startDate;
	@Column
	private LocalDateTime endDate;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Builder
	public Contract(Sales sales, Sales selfContracting, Engineer engineer, Company company, LocalDateTime startDate,
			LocalDateTime endDate)  {
		super();
		this.sales = sales;
		this.selfContracting = selfContracting;
		this.engineer = engineer;
		this.company = company;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public boolean isActiveOn(LocalDate date) {
        LocalDate start = this.getStartDate().toLocalDate();
        LocalDate end = this.getEndDate().toLocalDate();

        // 1. 시작일 이전이면 무조건 false
        if (date.isBefore(start)) {
            return false;
        }
        // 2. 기간 내에 있거나
        if (!date.isAfter(end)) {
            return true;
        }
        // 3. 기간은 지났지만, 같은 달이고 연장이 확정된 경우
        if (this.getExtension() 
        	&& YearMonth.from(end).equals(YearMonth.from(date))) {
            return true;
        }

        return false;
    }
	
	public boolean isOver(YearMonth month) {
		return YearMonth.from(this.endDate).equals(month);
	}
	
	public void terminateAfterEndDate() {
		this.extension = false;
	}

	@Override
	public boolean isNew() {
		return this.updatedAt == null;
	}
}
