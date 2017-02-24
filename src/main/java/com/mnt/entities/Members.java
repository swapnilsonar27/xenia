package com.mnt.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mnt.entities.auth.AuthUser;

@Entity
@Table(name="members")
public class Members {

	@Id
	@Column(name="memberId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int memberId;
	
	@Column(name="dob")
	public Date dob;
	
	@Column(name="underAgePlayer")
	public Boolean underAgePlayer;
	
	@JoinColumn(name="uaGuardian1")
	@ManyToOne
	public AuthUser guardian1;

	@JoinColumn(name="uaGuardian2")
	@ManyToOne
	public AuthUser guardian2;
	
	@JoinColumn(name="uaGuardian3")
	@ManyToOne
	public AuthUser guardian3;
	
	@Column(name="firstName")
	public String firstName;
	
	@Column(name="lastName")
	public String lastName;
	
	@Column(name="gender")
	public Boolean gender;
	
	@Column(name="currentTeam")
	public String currentTeam;
	
	@Column(name="repPlayer")
	public String repPlayer;
	
	@Column(name="repTeam")
	public String repTeam;

	@Column(name="newToTeam")
	public Boolean newToTeam;
	
	@Column(name="singletNumber", length=2)
	public Integer singletNumber;
	
	@Column(name="medicalCondition")
	public String medicalCondition;
	
}
