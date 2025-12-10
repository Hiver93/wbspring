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
	private Integer id;
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
	public Engineer(Integer id, String name, EngineerType type, Boolean companyHouse) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.companyHouse = companyHouse;
	}
	
	public boolean isType(String typeName) {
		return type.getName().equals(typeName);
	}
	
	// 해당월말에 유지되는 계약이 있는지?
	public boolean hasContractLastDayOf(YearMonth month) {
		LocalDate endOfMonth = month.atEndOfMonth();
		return hasContract(endOfMonth);
	}
	
	// 해당일에 유지되는 계약이 있는지?
	public boolean hasContract(LocalDate date) {
		return this.getContractList().stream()
	            .anyMatch(contract -> contract.isActiveOn(date));
	}
	
	// 해당월말에 회사에 고용된 상태인가
	public boolean isEmployedLastDayOf(YearMonth month) {
		LocalDate endOfMonth = month.atEndOfMonth();
		return	isEmployed(endOfMonth);
	}
	
	// 해당일에 회사에 고용된 상태인가 (퇴사일 당일은 ???)
	public boolean isEmployed(LocalDate date) {
		// 1. 입사일 데이터가 없는 경우 (예외적 상황)
	    if(this.getJoiningDate() == null) {
	        if(this.getLeavingDate() == null) {
	            return true; // 입/퇴사일 모두 없으면 재직 간주
	        }
	        // 퇴사일+1일이 기준일보다 미래여야 함 -> 즉, 퇴사일 당일까지 재직
	        else if(this.getLeavingDate().toLocalDate().plusDays(1).isAfter(date)) {
	            return true;
	        }
	    }
	    // 2. 입사일 데이터가 있는 경우
	    // 입사일 당일부터 재직 (입사일 <= date)
	    else if(this.getJoiningDate().toLocalDate().isBefore(date.plusDays(1))) {
	        if(this.getLeavingDate() == null) {
	            return true; // 퇴사일 없으면 계속 재직
	        }
	        // 퇴사일 당일까지 재직 (퇴사일 >= date)
	        else if(this.getLeavingDate().toLocalDate().plusDays(1).isAfter(date)) {
	            return true;
	        }
	    }
	    
	    // 그 외는 미재직
	    return false;
	}
	
	public boolean hasContractAfter(LocalDate date) {
		return contractList.stream().anyMatch(c->c.getStartDate().toLocalDate().isAfter(date));
	}
	
	public boolean isRetuneeAt(YearMonth month) {
		return this.contractList.stream().anyMatch(c->c.isOver(month));
	}
	
	private List<Contract> getContractOfTerminatedThisMonth(YearMonth month) {
		return this.contractList.stream().filter(c->c.isOver(month)).toList();
	}
	
	public boolean hasExtensionIn(YearMonth month) {
	    return getContractOfTerminatedThisMonth(month).stream()
	            .anyMatch(c -> c.getExtension()); // 혹은 c.isExtension()
	}
	
	public void setLeavingDate(LocalDateTime leavingDate) {
		this.leavingDate = leavingDate;
	}
}
