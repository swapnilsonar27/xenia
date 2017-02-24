package com.mnt.vm;

import java.util.List;

public class EventVM {

	public String id;
	public String eventDescription;
	public String startDate;
	public String endDate;
	public String frequency;
	public Boolean oneofevent;
	public List<ResponseVM> group;
	public List<ResponseVM> merchandise;
	public String eventPrice;
	public List<ResponseVM> organiser;
	public String company;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Boolean getOneofevent() {
		return oneofevent;
	}
	public void setOneofevent(Boolean oneofevent) {
		this.oneofevent = oneofevent;
	}
	public List<ResponseVM> getGroup() {
		return group;
	}
	public void setGroup(List<ResponseVM> group) {
		this.group = group;
	}
	public List<ResponseVM> getMerchandise() {
		return merchandise;
	}
	public void setMerchandise(List<ResponseVM> merchandise) {
		this.merchandise = merchandise;
	}
	public String getEventPrice() {
		return eventPrice;
	}
	public void setEventPrice(String eventPrice) {
		this.eventPrice = eventPrice;
	}
	public List<ResponseVM> getOrganiser() {
		return organiser;
	}
	public void setOrganiser(List<ResponseVM> organiser) {
		this.organiser = organiser;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
}
