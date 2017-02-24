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

@Entity
@Table(name="eventGroups")
public class EventGroups {
	
	@Id
	@Column(name="eventGroupId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int eventGroupId;
	
	@JoinColumn(name="eventId")
	@ManyToOne
	public Events event;
	
	@JoinColumn(name="groupId")
	@ManyToOne
	public Groups group;
	
	@Column(name="isPaid")
	public Boolean isPaid;
}
