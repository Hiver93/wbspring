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
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class EngineerJoining {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String type;
	@Column
	private Boolean shift;
	@OneToMany(mappedBy = "joining", fetch = FetchType.LAZY)
	private List<Engineer> engineerList = new ArrayList<>();
	@Builder
	public EngineerJoining(String type, Boolean shift) {
		super();
		this.type = type;
		this.shift = shift;
	}
	
	@Getter
	@RequiredArgsConstructor
	public static enum Type{
		THIS_MONTH("THIS_MONTH"), 
		NEXT_MONTH("NEXT_MONTH");
		private final String name;
	}
}
