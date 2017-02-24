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
@Table(name="payments")
public class Payments {
	
	@Id
	@Column(name="paymentId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int paymentId;
	
	@Column(name="paymentToken")
	public String paymentToken;
	
	@Column(name="amount")
	public Double amount;
	
	@JoinColumn(name="eventUserId")
	@ManyToOne
	public EventGroups eventUser;
	
}
