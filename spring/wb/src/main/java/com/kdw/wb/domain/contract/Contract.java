package com.kdw.wb.domain.contract;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Sales sales;
	@ManyToOne
	private Sales selfContracting;
	@OneToOne
	private Engineer engineer;
	@ManyToOne
	private Company company;
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
			LocalDateTime endDate) {
		super();
		this.sales = sales;
		this.selfContracting = selfContracting;
		this.engineer = engineer;
		this.company = company;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
