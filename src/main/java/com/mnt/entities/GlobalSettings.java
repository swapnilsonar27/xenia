package com.mnt.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="globalSettings")
public class GlobalSettings {

	@Id
	@Column(name="globalSettingId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int globalSettingId;
	
	@Column(name="gst")
	public Float gst;
	
}
