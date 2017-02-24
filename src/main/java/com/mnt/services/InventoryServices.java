package com.mnt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.dao.InventoryDao;
import com.mnt.entities.Inventories;
import com.mnt.vm.InventoryVM;
import com.mnt.vm.ResponseVM;

@Service
public class InventoryServices {

	@Autowired
	InventoryDao inventoryDao;
	
	public List<ResponseVM> getMerchandise() {
		return inventoryDao.getMerchandise();
	}
	
	public List<ResponseVM> getMerchandiseNameById(String id) {
		return inventoryDao.getMerchandiseNameById(id);
	}
	
	public List<InventoryVM> getInventoriesByEventId(String id) {
		return inventoryDao.getMerchandiseByEventId(id);
	}
	
	public List<InventoryVM> getAllInventories() {
		return inventoryDao.getAllInventories();
	}
	
	public void saveInventory(MultipartFile file, InventoryVM vm) {
		inventoryDao.saveInventory(file, vm);
	}

	public void deleteInventoryById(String id) {
		inventoryDao.deleteInventoryById(id);
		
	}
}
