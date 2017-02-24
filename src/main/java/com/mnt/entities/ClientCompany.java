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
@Table(name="clientCompany")
public class ClientCompany {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@JoinColumn(name="clientId")
	@ManyToOne
	public Guardians client;
	
	@JoinColumn(name="companyId")
	@ManyToOne
	public Companies company;
}
