package com.kdw.wb.domain.engineer;

import java.time.LocalDateTime;
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
	private List<Contract> contractList;
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
}
