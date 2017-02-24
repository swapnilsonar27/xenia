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
@Table(name="inventorySizes")
public class InventorySizes {
	
	@Id
	@Column(name="inventorySizeId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int inventoryId;
	
	@JoinColumn(name="inventoryId")
	@ManyToOne
	public Inventories inventory;
	
	@Column(name="sizeAvailable", length=4)
	public String sizeAvailable;
	
}
