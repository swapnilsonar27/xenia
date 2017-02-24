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
@Table(name="inventories")
public class Inventories {
	
	@Id
	@Column(name="inventoryId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int inventoryId;
	
	@JoinColumn(name="companyId")
	@ManyToOne
	public Companies company;
	
	@Column(name="description")
	public String description;
	
	@Column(name="unitSellingPrice")
	public Double unitSellingPrice;
	
	@Column(name="image_url")
	public String imageUrl;
	
	//sizeAvailable
}
