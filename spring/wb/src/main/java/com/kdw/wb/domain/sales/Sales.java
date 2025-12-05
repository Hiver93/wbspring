package com.kdw.wb.domain.sales;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kdw.wb.domain.contract.Contract;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
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
public class Sales {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique=true)
	private Integer no;
	@Column
	private String name;
	@OneToMany(mappedBy = "sales", fetch = FetchType.LAZY)
	private List<Contract> contractList = new ArrayList<>();
	@OneToMany(mappedBy = "selfContracting", fetch = FetchType.LAZY)
	private List<Contract> selfContracingList = new ArrayList<>();
	@ManyToOne
	private SalesStatus status;
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
	@Builder
	public Sales(Integer no, String name, SalesStatus status) {
		super();
		this.no = no;
		this.name = name;
		this.status = status;
	}
	
	
}