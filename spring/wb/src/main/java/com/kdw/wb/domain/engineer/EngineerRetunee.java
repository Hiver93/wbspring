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
public class EngineerRetunee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String type;
	@Column
	private Boolean extension;
	@Column
	private Boolean isReturned;
	@OneToMany(mappedBy = "returnee", fetch = FetchType.LAZY)
	private List<Engineer> engineerList = new ArrayList<>();
	@Builder
	public EngineerRetunee(String type, Boolean extension, Boolean isReturned) {
		super();
		this.type = type;
		this.extension = extension;
		this.isReturned = isReturned;
	}
	
	@Getter
	@RequiredArgsConstructor
	public static enum Type{
		SHIFT_DECIDED("SHIFT_DECIDED"), 
		SHIFT_UNDECIDED("SHIFT_UNDECIDED"),
		MOVING("MOVING"),
		RESIGNATION("RESIGNATION"),
		LEAVING("LEAVING");
		private final String name;
	}
}
