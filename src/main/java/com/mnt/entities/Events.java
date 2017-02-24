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
@Table(name="events")
public class Events {
	
	@Id
	@Column(name="eventId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int eventId;
	
	@Column(name="eventDescription")
	public String eventDescription;
	
	@JoinColumn(name="companyId")
	@ManyToOne
	public Companies company;
	
	@JoinColumn(name="companyOrganiserId")
	@ManyToOne
	public CompanyOrganisers companyOrganiser;
	
	@Column(name="startDate")
	public Date startDate;
	
	@Column(name="endDate")
	public Date endDate;
	
	@Column(name="recurring")
	public String recurring;
	
	@Column(name="eventPrice")
	public Double eventPrice;
	
	@Column(name="inventoryAvailable")
	public Boolean inventoryAvailable;
	
}
