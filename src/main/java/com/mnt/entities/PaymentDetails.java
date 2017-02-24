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
@Table(name="paymentDetails")
public class PaymentDetails {
	
	@Id
	@Column(name="paymentDetailId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int paymentDetailId;
	
	@JoinColumn(name="paymentId")
	@ManyToOne
	public Payments payment;
	
	@JoinColumn(name="inventoryId")
	@ManyToOne
	public Inventories inventory;
	
	@JoinColumn(name="eventUserId")
	@ManyToOne
	public EventGroups eventUser;
	
}