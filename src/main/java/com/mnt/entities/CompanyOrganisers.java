package com.mnt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="companyOrganisers")
public class CompanyOrganisers {

	@Id
	@Column(name="companyOrganiserId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int companyOrganiserId;
	
	@JoinColumn(name="companyId")
	@ManyToOne
	public Companies company;
	
	@Column(name="firstName")
	public String firstName;
	
	@Column(name="lastName")
	public String lastName;
	
	@Column(name="email")
	public String email;
	
	@Column(name="mobile")
	public String mobile;
	
}
