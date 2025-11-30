package com.kdw.wb.domain.sales;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdw.wb.domain.company.Company;
import com.kdw.wb.domain.engineer.Engineer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class SalesStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;
	@Builder
	public SalesStatus(String name) {
		super();
		this.name = name;
	}
}
