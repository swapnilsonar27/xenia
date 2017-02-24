package com.mnt.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.auth.TokenHandler;
import com.mnt.entities.Companies;
import com.mnt.entities.CompanyOrganisers;
import com.mnt.entities.EventGroups;
import com.mnt.entities.EventMerchandise;
import com.mnt.entities.EventOrganiser;
import com.mnt.entities.Events;
import com.mnt.entities.GroupMembers;
import com.mnt.entities.Groups;
import com.mnt.entities.Inventories;
import com.mnt.vm.EventMembersVM;
import com.mnt.vm.EventUserVM;
import com.mnt.vm.EventVM;
import com.mnt.vm.ResponseVM;

@Repository
@Transactional
@Component("eventDao")
public class EventDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<EventMembersVM> saveEvent(EventVM vm){
		Date today = null;
		boolean sendNotification = false;
		
		Session session = sessionFactory.getCurrentSession();
		Events event = new Events();
		event.eventDescription = vm.getEventDescription();
		event.recurring = vm.getFrequency();
		event.eventPrice = Double.parseDouble(vm.getEventPrice());
		Companies cmp = (Companies) session.get(Companies.class, Integer.parseInt(vm.getCompany()+""));
		event.company = cmp;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			event.startDate = df.parse(vm.getStartDate());
			if(vm.getOneofevent() == null || !vm.getOneofevent()){
				event.endDate = df.parse(vm.getEndDate());
			}
			String todayStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			today = df.parse(todayStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		session.save(event);
		System.out.println(today);
		System.out.println(event.startDate);
		if(today.equals(event.startDate)){
			sendNotification = true;	
		}
		
		List<EventMembersVM> returnlist = new ArrayList<>();
		for (ResponseVM group : vm.getGroup()) {
			EventGroups eu = new EventGroups();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			eu.event = eve;
			Groups gr = (Groups) session.get(Groups.class, group.id);
			eu.group = gr;
			eu.isPaid = false;

			if(sendNotification){
				Query selectQuery = session.createQuery("from GroupMembers where groupId= :id");
				selectQuery.setParameter("id", group.id);
				List<GroupMembers> groupMembers = selectQuery.list();
				for (GroupMembers groupMember : groupMembers) {
					if(groupMember.member.guardian1 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian1.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						System.out.println(groupMember.member.guardian1.getGuardian().guardianId);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian1.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian1.getEmail());
						returnVm.setFirstname(groupMember.member.guardian1.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian1.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian1.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}
					
					if(groupMember.member.guardian2 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian2.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian2.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian2.getEmail());
						returnVm.setFirstname(groupMember.member.guardian2.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian2.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian2.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}

					if(groupMember.member.guardian3 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian3.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian3.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian3.getEmail());
						returnVm.setFirstname(groupMember.member.guardian3.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian3.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian3.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}
				}
				
			}
			session.save(eu);
		}
		
		for (ResponseVM merchandise : vm.getMerchandise()) {
			EventMerchandise em = new EventMerchandise();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			em.event = eve;
			Inventories inv = (Inventories) session.get(Inventories.class, merchandise.id);
			em.inventory = inv;
			session.save(em);
		}
		
		for (ResponseVM organiser : vm.getOrganiser()) {
			EventOrganiser eo = new EventOrganiser();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			eo.event = eve;
			CompanyOrganisers cmporg = (CompanyOrganisers) session.get(CompanyOrganisers.class, organiser.getId());
			eo.companyOrganiser = cmporg;
			session.save(eo);
		}
		
		if(sendNotification) {
			return returnlist;
		}
		return null; 
	}
	
	public List<EventMembersVM> updateEvent(EventVM vm){
		Events event = (Events) sessionFactory.getCurrentSession().get(Events.class, Integer.parseInt(vm.getId()));
		System.out.println("event id is "+event.eventId);
		Date today = null;
		boolean sendNotification = false;
		
		Session session = sessionFactory.getCurrentSession();
		
		event.eventDescription = vm.getEventDescription();
		event.recurring = vm.getFrequency();
		event.eventPrice = Double.parseDouble(vm.getEventPrice());
		Companies cmp = (Companies) session.get(Companies.class, Integer.parseInt(vm.getCompany()+""));
		event.company = cmp;
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			event.startDate = df.parse(vm.getStartDate());
			if(vm.getOneofevent() == null || !vm.getOneofevent()){
				event.endDate = df.parse(vm.getEndDate());
			}else{
				event.endDate = null;
			}
			String todayStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			today = df.parse(todayStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		session.update(event);

		if(today.equals(event.startDate)){
			sendNotification = true;	
		}
		
		deleteEventGroup(vm.getId());
		
		List<EventMembersVM> returnlist = new ArrayList<>();
		for (ResponseVM group : vm.getGroup()) {
			EventGroups eu = new EventGroups();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			eu.event = eve;
			Groups gr = (Groups) session.get(Groups.class, group.id);
			eu.group = gr;
			eu.isPaid = false;

			if(sendNotification){
				Query selectQuery = session.createQuery("from GroupMembers where groupId= :id");
				selectQuery.setParameter("id", group.id);
				List<GroupMembers> groupMembers = selectQuery.list();
				for (GroupMembers groupMember : groupMembers) {
					if(groupMember.member.guardian1 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian1.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						System.out.println(groupMember.member.guardian1.getGuardian().guardianId);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian1.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian1.getEmail());
						returnVm.setFirstname(groupMember.member.guardian1.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian1.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian1.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}
					
					if(groupMember.member.guardian2 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian2.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian2.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian2.getEmail());
						returnVm.setFirstname(groupMember.member.guardian2.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian2.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian2.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}

					if(groupMember.member.guardian3 != null) {
						
						String hql = "UPDATE Guardians set token = :token, tokenUpdatedOn = :today "  + 
					             "WHERE guardianId = :guardianId";
						Query queryUpdate = session.createQuery(hql);
						queryUpdate.setParameter("token", TokenHandler.createTokenForUserEvent(groupMember.member.guardian3.getUsername(), eu.event.eventDescription));
						queryUpdate.setParameter("today", today);
						queryUpdate.setParameter("guardianId", groupMember.member.guardian3.getGuardian().guardianId);
						queryUpdate.executeUpdate();
						
						EventMembersVM returnVm = new EventMembersVM();
						returnVm.setEmail(groupMember.member.guardian3.getEmail());
						returnVm.setFirstname(groupMember.member.guardian3.getGuardian().firstName);
						returnVm.setLastname(groupMember.member.guardian3.getGuardian().lastName);
						returnVm.setGuardianId(groupMember.member.guardian3.getGuardian().guardianId+"");
						returnlist.add(returnVm);
					}
				}
				
			}
			session.save(eu);
		}
		
		deleteEventInventory(vm.getId());
		for (ResponseVM merchandise : vm.getMerchandise()) {
			EventMerchandise em = new EventMerchandise();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			em.event = eve;
			Inventories inv = (Inventories) session.get(Inventories.class, merchandise.id);
			em.inventory = inv;
			session.save(em);
		}
		
		deleteEventOrganiser(vm.getId());
		for (ResponseVM organiser : vm.getOrganiser()) {
			EventOrganiser eo = new EventOrganiser();
			Events eve = (Events) session.get(Events.class, Integer.parseInt(event.eventId+""));
			eo.event = eve;
			CompanyOrganisers cmporg = (CompanyOrganisers) session.get(CompanyOrganisers.class, organiser.getId());
			eo.companyOrganiser = cmporg;
			session.save(eo);
		}
		
		if(sendNotification) {
			return returnlist;
		}
		return null; 
	}
	
	
	public List<EventVM> getAllEvents(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Events";
		Query query = session.createQuery(hql);
		List<EventVM> results = query.list();
		
		return results;
	}
	
	public EventVM getEventDetailsById(String id){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Events where eventId=:id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Integer.parseInt(id));
		Events result = (Events) query.uniqueResult();
		
		List<ResponseVM> groupList = new ArrayList<>();
		List<ResponseVM> inventoryList = new ArrayList<>();
		List<ResponseVM> organiserList = new ArrayList<>();
		
		String ghql = "FROM EventGroups where event.eventId=:id";
		Query gquery = session.createQuery(ghql);
		gquery.setParameter("id", Integer.parseInt(id));
		List<EventGroups> groups = gquery.list();
		
		for (EventGroups eventGroups : groups) {
			ResponseVM groupvm = new ResponseVM();
			groupvm.setId(eventGroups.group.groupId);
			groupvm.setLabel(eventGroups.group.name);
			groupList.add(groupvm);
		}
		
		String mhql = "FROM EventMerchandise where event.eventId=:id";
		Query mquery = session.createQuery(mhql);
		mquery.setParameter("id", Integer.parseInt(id));
		List<EventMerchandise> merchandise = mquery.list();
		
		for (EventMerchandise eventMerchandise : merchandise) {
			ResponseVM invvm = new ResponseVM();
			invvm.setId(eventMerchandise.inventory.inventoryId);
			invvm.setLabel(eventMerchandise.inventory.description);
			inventoryList.add(invvm);
		}
		
		String ohql = "FROM EventOrganiser where event.eventId=:id";
		Query oquery = session.createQuery(ohql);
		oquery.setParameter("id", Integer.parseInt(id));
		List<EventOrganiser> organiser = oquery.list();
		
		for (EventOrganiser eventOrganise : organiser) {
			ResponseVM orgvm = new ResponseVM();
			orgvm.setId(eventOrganise.companyOrganiser.companyOrganiserId);
			orgvm.setLabel(eventOrganise.companyOrganiser.firstName+" "+eventOrganise.companyOrganiser.lastName);
			organiserList.add(orgvm);
		}
		EventVM resvm = new EventVM();
		resvm.id = result.eventId+"";
		resvm.startDate = result.startDate+"";
		resvm.eventDescription = result.eventDescription;
		resvm.eventPrice = result.eventPrice+"";
		resvm.company = result.company.companyId+"";
		resvm.frequency = result.recurring+"";
		resvm.group = groupList;
		resvm.merchandise = inventoryList;
		resvm.organiser = organiserList;
		if(result.endDate == null){
			resvm.endDate = "";
			resvm.oneofevent = true;
		}else{
			resvm.endDate = result.endDate+"";
			resvm.oneofevent = false;
		}
		
		return resvm;
	}
	public EventUserVM getDetailsByToken(String token){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT au.id AS authId, au.name AS name, au.email AS email, e.eventId AS eventId, e.eventDescription AS eventDescription, e.eventPrice AS eventPrice, eu.isPaid AS isPaid, eu.eventUserId AS eventUserId FROM AuthUser au, EventUsers eu, Events e WHERE eu.user.id = au.id AND e.eventId = eu.event.eventId AND eu.token = :token";
		Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(EventUserVM.class));
		query.setParameter("token", token);
		List<EventUserVM> users = query.list();
		
		if(users.size() > 0){
			users.get(0).setToken(token);
			return users.get(0);
		}
		return new EventUserVM();
	}
	
	public List<EventUserVM> getChildUsersByParenntId(String parentId, String eventId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT au.id AS authId, au.name AS name, au.email AS email, e.eventId AS eventId, e.eventDescription AS eventDescription, e.eventPrice AS eventPrice, eu.isPaid AS isPaid, eu.eventUserId AS eventUserId FROM AuthUser au, EventUsers eu, Events e WHERE eu.user.id = au.id AND e.eventId = eu.event.eventId AND au.parent.id = :parentId AND e.eventId = :eventId";
		Query query = session.createQuery(hql).setResultTransformer(Transformers.aliasToBean(EventUserVM.class));
		query.setParameter("parentId", Long.parseLong(parentId));
		query.setParameter("eventId", Integer.parseInt(eventId));
		List<EventUserVM> users = query.list();
		
		return users;
	}
	
	public void deleteEventGroup(String eventid){
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM EventGroups "  + 
	             "WHERE event.eventId = :eventid";
		Query query = session.createQuery(hql);
		query.setParameter("eventid", Integer.parseInt(eventid));
		query.executeUpdate();
	}
	
	public void deleteEventInventory(String eventid){
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM EventMerchandise "  + 
	             "WHERE event.eventId = :eventid";
		Query query = session.createQuery(hql);
		query.setParameter("eventid", Integer.parseInt(eventid));
		query.executeUpdate();
	}
	
	public void deleteEventOrganiser(String eventid){
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM EventOrganiser "  + 
	             "WHERE event.eventId = :eventid";
		Query query = session.createQuery(hql);
		query.setParameter("eventid", Integer.parseInt(eventid));
		query.executeUpdate();
	}
	
	/*public List<EventUsers> getScheduledDataByFreq(String freq) throws ParseException{
		Session session = sessionFactory.getCurrentSession();
		String hql = "Select eu from Events e, EventUsers eu where e.recurring=:freq and eu.event = e.eventId and e.startDate <= :today";
		Query query = session.createQuery(hql);
		query.setParameter("freq", freq);
		query.setParameter("today", getTodayDate());
		List<EventUsers> events = query.list();
		
		List<EventUsers> eventsList = new ArrayList<EventUsers>();
		for (EventUsers eventUsers : events) {
			if(eventUsers.event.endDate == null || eventUsers.event.endDate.after(getTodayDate())){
				eventsList.add(eventUsers);
			}
		}
		
		return eventsList;
	}*/
	
	public void updateToken(String token, int id){
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE EventUsers set token = :token, tokenUpdatedOn = :today"  + 
	             " WHERE eventUserId = :id";
		Query query = session.createQuery(hql);
		query.setParameter("token", token);
		query.setParameter("today", new Date());
		query.setParameter("id", id);
		query.executeUpdate();
		
	}
	
	public Date getTodayDate() throws ParseException{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String todayStr = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		return df.parse(todayStr);
	}
	
	
	
	public void updateUserPaidStatusById(String id, Boolean flag) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE EventUsers set isPaid = :isPaid "  + 
	             "WHERE eventUserId = :id";
		Query query = session.createQuery(hql);
		query.setParameter("isPaid", flag);
		query.setParameter("id", Integer.parseInt(id));
		query.executeUpdate();
		
	}
}
