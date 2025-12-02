package com.kdw.wb.domain.engineer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class EngineerType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String name;
	@OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
	private List<Engineer> engineerList = new ArrayList<>();
	
	@Builder
	public EngineerType(String name) {
		super();
		this.name = name;
	}
}
