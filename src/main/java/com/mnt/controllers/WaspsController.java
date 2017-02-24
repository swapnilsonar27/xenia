package com.mnt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mnt.entities.CompanyOrganisers;
import com.mnt.entities.Groups;
import com.mnt.services.CompanyServices;
import com.mnt.services.EventServices;
import com.mnt.services.GroupServices;
import com.mnt.services.InventoryServices;
import com.mnt.services.MailIntegrationServices;
import com.mnt.services.OrganiserServices;
import com.mnt.services.PaymentServices;
import com.mnt.services.UserServices;
import com.mnt.vm.EventVM;
import com.mnt.vm.InventoryVM;
import com.mnt.vm.ResponseVM;

@Controller
@CrossOrigin(origins = "*")
public class WaspsController {
	
	@Autowired
	UserServices userService;
	
	@Autowired
	InventoryServices inventoryServices;
	
	@Autowired
	EventServices eventServices;

	@Autowired
	MailIntegrationServices mailIntegrationServices;
	
	@Autowired
	PaymentServices paymentServices;
	
	@Autowired
	CompanyServices companyServices;
	
	@Autowired
	GroupServices groupServices;
	
	@Autowired
	OrganiserServices organiserServices;
	
	
	/**************Event API*****************/
	@Transactional
	@RequestMapping(value="wasps/event/save",method=RequestMethod.POST)
	@ResponseBody
	public void saveEvent(@RequestBody EventVM vm) {
		eventServices.saveEvent(vm);
	}
	
	@Transactional
	@RequestMapping(value="wasps/event/update",method=RequestMethod.POST)
	@ResponseBody
	public void updateEvent(@RequestBody EventVM vm) {
		eventServices.updateEvent(vm);
	}
	
	@Transactional
	@RequestMapping(value="wasps/event/all",method=RequestMethod.GET)
	@ResponseBody
	public List<EventVM> getAllEvents() {
		return eventServices.getAllEvents();
	}
	
	@Transactional
	@RequestMapping(value="wasps/merchandise/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getEventById(@PathVariable("id") String id) {
		return inventoryServices.getMerchandiseNameById(id);
	}
	
	@Transactional
	@RequestMapping(value="wasps/group/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getGroupById(@PathVariable("id") String id) {
		return groupServices.getGroupNameByEventId(id);
	}
	
	@Transactional
	@RequestMapping(value="wasps/organiserByCompany/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<CompanyOrganisers> getOrganiserByCompanyId(@PathVariable("id") String id) {
		return organiserServices.getOrganiserByCompany(id);
	}
	
	@Transactional
	@RequestMapping(value="wasps/event/edit/{id}",method=RequestMethod.GET)
	@ResponseBody
	public EventVM getEventDetailsById(@PathVariable("id") String id) {
		return eventServices.getEventDetailsById(id);
	}
	
	@Transactional
	@RequestMapping(value="wasps/getMerchendise",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getMerchendise() {
		return inventoryServices.getMerchandise();
	}
	
	@Transactional
	@RequestMapping(value="wasps/groupByCompany/{companyId}",method=RequestMethod.GET)
	@ResponseBody
	public List<Groups> getGroupsByCompany(@PathVariable("companyId") String id) {
		return groupServices.getGroupByCompany(id);
	}
	
	/*@Transactional
	@RequestMapping(value="wasps/getUsers",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getUsers() {
		return userService.getUsers();
	}*/
	
	/*@Transactional
	@RequestMapping(value="user/token/{token:.+}",method=RequestMethod.GET)
	@ResponseBody
	public EventUserVM getEmailByToken(@PathVariable("token") String token) {
		return eventServices.getDetailsByToken(token);
	}
	
	@Transactional
	@RequestMapping(value="wasps/user/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getUserById(@PathVariable("id") String id) {
		return userService.getUserNameById(id);
	}
	
	@Transactional
	@RequestMapping(value="wasps/getChildUsers/{parentId}/{eventId}",method=RequestMethod.GET)
	@ResponseBody
	public List<EventUserVM> getChildUsers(@PathVariable("parentId") String parentId, @PathVariable("eventId") String eventId) {
		return eventServices.getChildUsersByParenntId(parentId, eventId);
	}*/
	
	@Transactional
	@RequestMapping(value="wasps/inventoriesByEvent/{eventId}",method=RequestMethod.GET)
	@ResponseBody
	public List<InventoryVM> getInventoriesByEventId(@PathVariable("eventId") String id) {
		return inventoryServices.getInventoriesByEventId(id);
	}
	

	@Transactional
	@RequestMapping(value="wasps/deleteInventoryById/{inventoryId}",method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteInventoryById(@PathVariable("inventoryId") String id) {
		 inventoryServices.deleteInventoryById(id);
	}
	
	/*@Transactional
	@RequestMapping(value="user/savepayinfo",method=RequestMethod.POST)
	public String getToken(@RequestParam("EWAY_ACCESSCODE") String accesscode, @RequestParam("total_amount") Double total, @RequestParam("event_user_id") Integer eventUserId, @RequestParam("paid_event_user_id") String paidUsers) {
		paymentServices.savePaymentInfo(accesscode, total, eventUserId, paidUsers);
		 return "redirect:/thankyou.html";
	}*/
	
	@Transactional
	@RequestMapping(value="wasps/inventory/all",method=RequestMethod.GET)
	@ResponseBody
	public List<InventoryVM> getAllInventories() {
		return inventoryServices.getAllInventories();
	}
	
	@Transactional
	@RequestMapping(value="wasps/inventory/save",method=RequestMethod.POST)
	@ResponseBody
	public void saveInventory(@RequestParam("file") MultipartFile file, InventoryVM vm) {
		 inventoryServices.saveInventory(file, vm);
	}
	
	@Transactional
	@RequestMapping(value="wasps/company/list",method=RequestMethod.GET)
	@ResponseBody
	public List<ResponseVM> getCompanies() {
		return companyServices.getAllCompanies();
	}
	
	/*@Transactional
	@RequestMapping(value="wasps/user/all",method=RequestMethod.GET)
	@ResponseBody
	public List<UserVM> getAllUserDetails() {
		return userService.getAllUserDetails();
	}
	
	@Transactional
	@RequestMapping(value="wasps/user/save",method=RequestMethod.POST)
	@ResponseBody
	public void saveUser(@RequestBody UserVM vm) {
		userService.saveUser(vm);
	}*/
	
}