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
@Table(name="groups")
public class Groups {

	@Id
	@Column(name="groupId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int groupId;
	
	@Column(name="name")
	public String name;
	
	@JoinColumn(name="company")
	@ManyToOne
	public Companies company;
	
}
