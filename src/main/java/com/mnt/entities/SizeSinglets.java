package com.mnt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sizeSinglets")
public class SizeSinglets {
	
	@Id
	@Column(name="sizeSingletId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int sizeSingletId;
	
	@Column(name="size", length=4)
	public String size;
	
	@Column(name="chest")
	public Integer chest;
	
	@Column(name="body")
	public Integer body;
	
}
