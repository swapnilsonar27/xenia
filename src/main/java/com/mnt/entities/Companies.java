package com.mnt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="companies")
public class Companies {
	
	@Id
	@Column(name="companyId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int companyId;
	
	@Column(name="alias")
	public String alias;
	
	@Column(name="companyName")
	public String companyName;
	
}
