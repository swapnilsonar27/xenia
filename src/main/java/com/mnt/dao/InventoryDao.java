package com.mnt.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.entities.Companies;
import com.mnt.entities.Inventories;
import com.mnt.services.CommonServices;
import com.mnt.vm.InventoryVM;
import com.mnt.vm.ResponseVM;

@Repository
@Transactional
@Component("inventoryDao")
public class InventoryDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CommonServices commonServices;
	
	public List<ResponseVM> getMerchandise() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from Inventories");
		List<Inventories> inventory = selectQuery.list();
		List<ResponseVM> merchandiseList= new ArrayList<ResponseVM>();
		for (Inventories merchandise : inventory) {
			ResponseVM vm = new ResponseVM();
			vm.setId(merchandise.inventoryId);
			vm.setLabel(merchandise.description+"");
			merchandiseList.add(vm);
		}
		
		return merchandiseList;
	}
	
	public List<ResponseVM> getMerchandiseNameById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("Select inv from Inventories inv, EventMerchandise em where em.event.eventId=:eventId and em.inventory.inventoryId=inv.inventoryId");
		selectQuery.setParameter("eventId", Integer.parseInt(id));
		List<Inventories> user = selectQuery.list();
		List<ResponseVM> userList= new ArrayList<ResponseVM>();
		for (Inventories invt : user) {
			ResponseVM vm = new ResponseVM();
			vm.setId(invt.inventoryId);
			vm.setLabel(invt.description+"");
			userList.add(vm);
		}
		
		return userList;
	}
	
	public List<InventoryVM> getMerchandiseByEventId(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("Select inv from Inventories inv, EventMerchandise em where em.event.eventId=:eventId and em.inventory.inventoryId=inv.inventoryId");
		selectQuery.setParameter("eventId", Integer.parseInt(id));
		List<Inventories> inventory = selectQuery.list();
		List<InventoryVM> inventoryVm= new ArrayList<InventoryVM>();
		for (Inventories invt : inventory) {
			InventoryVM vm = new InventoryVM();
			vm.setCompanyId(invt.company.companyId+"");
			vm.setDescription(invt.description);
			vm.setImageUrl(invt.imageUrl);
			vm.setInventoryId(invt.inventoryId+"");
			vm.setUnitSellingPrice(invt.unitSellingPrice+"");
			inventoryVm.add(vm);
		}
		
		return inventoryVm;
	}
	
	public List<InventoryVM> getAllInventories(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Inventories";
		Query query = session.createQuery(hql);
		List<InventoryVM> results = query.list();
		
		return results;
	}
	
	public void saveInventory(MultipartFile file, InventoryVM vm){
		Session session = sessionFactory.getCurrentSession();
		Inventories inv = new Inventories();
		inv.description = vm.getDescription();
		inv.unitSellingPrice = Double.parseDouble(vm.getUnitSellingPrice());
		Companies company = (Companies) session.get(Companies.class, Integer.parseInt(vm.getCompanyId()));
		inv.company = company;
		session.save(inv);
		inv.imageUrl = commonServices.setImage(file, "inventory_images", inv.inventoryId);
		System.out.println(inv.imageUrl);
		session.update(inv);
		
	}

	public void deleteInventoryById(String id) {
		try{
		Inventories inventories = (Inventories) sessionFactory.getCurrentSession().get(Inventories.class, Integer.parseInt(id));	
			sessionFactory.getCurrentSession().delete(inventories);
		}catch(Exception e){
			System.out.println("Exception for delete");
		}
		
		
	}
}
