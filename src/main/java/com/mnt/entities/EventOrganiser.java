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
@Table(name="eventOrganiser")
public class EventOrganiser {
	
	@Id
	@Column(name="eventOrganiserId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int eventOrganiserId;
	
	@JoinColumn(name="eventId")
	@ManyToOne
	public Events event;
	
	@JoinColumn(name="companyOrganiserId")
	@ManyToOne
	public CompanyOrganisers companyOrganiser;
}
