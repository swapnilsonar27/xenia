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
@Table(name="groupMembers")
public class GroupMembers {

	@Id
	@Column(name="groupMemberId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int groupId;
	
	@JoinColumn(name="groupId")
	@ManyToOne
	public Groups group;
	
	@JoinColumn(name="memberId")
	@ManyToOne
	public Members member;
	
}
