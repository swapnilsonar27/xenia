package com.mnt.vm;

public class InventoryVM {

	public String inventoryId;
	public String description;
	public String unitSellingPrice;
	public String companyId;
	public String imageUrl;
	
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnitSellingPrice() {
		return unitSellingPrice;
	}
	public void setUnitSellingPrice(String unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
