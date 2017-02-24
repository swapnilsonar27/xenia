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
@Table(name="guardianMember")
public class GuardianMember {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@JoinColumn(name="guardianId")
	@ManyToOne
	public Guardians guardian;
	
	@JoinColumn(name="memberId")
	@ManyToOne
	public Members member;
	
}
