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
@Table(name="eventMerchandise")
public class EventMerchandise {
	
	@Id
	@Column(name="eventMerchandiseId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int eventMerchandiseId;
	
	@JoinColumn(name="eventId")
	@ManyToOne
	public Events event;
	
	@JoinColumn(name="inventoryId")
	@ManyToOne
	public Inventories inventory;
}
