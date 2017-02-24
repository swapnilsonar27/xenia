package com.mnt.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mnt.auth.TokenHandler;
import com.mnt.dao.EventDao;
import com.mnt.dao.GroupDao;
import com.mnt.entities.Groups;
import com.mnt.vm.EventMembersVM;
import com.mnt.vm.EventUserVM;
import com.mnt.vm.EventVM;
import com.mnt.vm.ResponseVM;

@EnableScheduling
@Component
@Service
public class EventServices {

	@Autowired
	EventDao eventDao;
	
	@Autowired
	GroupDao groupDao;
	
	@Autowired
	MailIntegrationServices mailIntegrationServices;
	
	public void saveEvent(EventVM vm){
		List<EventMembersVM> list = eventDao.saveEvent(vm);
		if(list != null){
			mailIntegrationServices.sendEventCreationNotification(list);
		}	
	}
	
	public void updateEvent(EventVM vm){
		List<EventMembersVM> list = eventDao.updateEvent(vm);
		if(list != null){
			mailIntegrationServices.sendEventCreationNotification(list);
		}
	}
	
	public List<EventVM> getAllEvents(){
		return eventDao.getAllEvents();
	}
	
	public EventVM getEventDetailsById(String id){
		return eventDao.getEventDetailsById(id);
	}
	
	public EventUserVM getDetailsByToken(String token){
		return eventDao.getDetailsByToken(token);
	}
	
	public List<EventUserVM> getChildUsersByParenntId(String parentId, String eventId){
		return eventDao.getChildUsersByParenntId(parentId,eventId);
	}
	
//	public List<EventUsers> getDailyScheduledData() throws ParseException{
//		List<EventUsers> events = eventDao.getScheduledDataByFreq("daily");
//		List<EventUsers> filtered = new ArrayList<EventUsers>();
//		for (EventUsers event : events) {
//			if(event.tokenUpdatedOn != null){
//				long days = getDiffInDays(new Date(), event.tokenUpdatedOn);
//				if(days >= 1){
//					filtered.add(event);
//				}
//			}else {
//				filtered.add(event);
//			}
//		}
//		return filtered;
//	}
//	
//	public List<EventUsers> getWeeklyScheduledData() throws ParseException{
//		List<EventUsers> events = eventDao.getScheduledDataByFreq("weekly");
//		List<EventUsers> filtered = new ArrayList<EventUsers>();
//		for (EventUsers event : events) {
//			if(event.tokenUpdatedOn != null){
//				long days = getDiffInDays(new Date(), event.tokenUpdatedOn);
//				if(days > 7){
//					filtered.add(event);
//				}
//			}else {
//				filtered.add(event);
//			}
//		}
//		return filtered;
//	}
//	
//	public List<EventUsers> getMonthlyScheduledData() throws ParseException{
//		List<EventUsers> events = eventDao.getScheduledDataByFreq("monthly");
//		List<EventUsers> filtered = new ArrayList<EventUsers>();
//		for (EventUsers event : events) {
//			if(event.tokenUpdatedOn != null){	
//				long days = getDiffInDays(new Date(), event.tokenUpdatedOn);
//				if(days > 30){
//					filtered.add(event);
//				}
//			}else {
//				filtered.add(event);
//			}
//		}
//		return filtered;
//	}
//	
//	public List<EventUsers> getAnnualScheduledData() throws ParseException{
//		List<EventUsers> events = eventDao.getScheduledDataByFreq("annual");
//		List<EventUsers> filtered = new ArrayList<EventUsers>();
//		for (EventUsers event : events) {
//			if(event.tokenUpdatedOn != null){	
//				long days = getDiffInDays(new Date(), event.tokenUpdatedOn);
//				if(days > 365){
//					filtered.add(event);
//				}
//			}else {
//				filtered.add(event);
//			}	
//		}
//		return filtered;
//	}
//	
//	public long getDiffInDays(Date start, Date end){
//		long miliSecondForStart = start.getTime();
//		long miliSecondForEnd = end.getTime();
//
//		// Calculate the difference in millisecond between two dates
//		long diffInMilis = miliSecondForStart - miliSecondForEnd;
//
//		long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
//		return diffInDays;
//	}
//	
//	@Scheduled(cron="0 52 20 * * ?")
//	//@Scheduled(fixedDelay=10000)
//	public void getScheduledBroadcast() throws ParseException {
//		System.out.println("============IN SCHEDULAR===============");
//		List<EventUsers> scheduledList = new ArrayList<EventUsers>();
//		
//		scheduledList.addAll(getAnnualScheduledData());
//		scheduledList.addAll(getMonthlyScheduledData());
//		scheduledList.addAll(getWeeklyScheduledData());
//		scheduledList.addAll(getDailyScheduledData());
//		
//		LinkedHashMap<String,List<String>> hm=new LinkedHashMap<String,List<String>>();  
//		for (EventUsers events : scheduledList) {
//			/*for update token*/
//			eventDao.updateToken(TokenHandler.createTokenForUserEvent(
//					events.user.getUsername(), events.event.eventDescription), events.eventUserId);
//			
//			/*for notification*/
//			List<String> userDetails = new ArrayList<String>();
//			userDetails.add(events.user.getName());
//			userDetails.add(events.user.getEmail());
//			userDetails.add(events.eventUserId+"");
//			hm.put(events.user.getEmail(), userDetails);
//		}
//		mailIntegrationServices.sendScheduledNotification(hm);
//	}
//	
//	public String getEventToken(String id){
//		return eventDao.getTokenById(id);
//	}
	
}
