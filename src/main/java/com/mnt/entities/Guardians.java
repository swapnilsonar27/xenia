package com.mnt.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="guardians")
public class Guardians {
	
	@Id
	@Column(name="guardianId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int guardianId;
	
	@Column(name="relationship")
	public String relationship;
	
	@Column(name="firstName")
	public String firstName;
	
	@Column(name="lastName")
	public String lastName;
	
	@Column(name="addressLine1")
	public String addressLine1;
	
	@Column(name="addressLine2")
	public String addressLine2;
	
	@Column(name="city")
	public String city;
	
	@Column(name="state")
	public String state;
	
	@Column(name="postcode")
	public Integer postcode;
	
	@Column(name="mobile")
	public String mobile;
	
	@Column(name="otherNumber")
	public String otherNumber;
	
	@Column(name="token")
	public String token;
	
	@Column(name="tokenUpdatedOn")
	public Date tokenUpdatedOn;
}
