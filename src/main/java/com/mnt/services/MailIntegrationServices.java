package com.mnt.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mnt.entities.Groups;
import com.mnt.vm.EventMembersVM;
import com.mnt.vm.ResponseVM;

@Service
public class MailIntegrationServices {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Value("${apikey}")
	private String apiKey;
	
	@Value("${listid}")
	private String listId;
	
	@Value("${event.creation.templateid}")
	private String eventCreationTemplateId;
	
	@Value("${list.startdate}")
	private String listStartdate;
	
	@Value("${list.redirectUrl}")
	private String redirectUrl;
	
	@Value("${list.fromEmail}")
	private String fromEmail;
	
	@Autowired
	EventServices eventServices;
	
	@Autowired 
	GuardianServices guardianServices;
	
	private IMailChimpServices	mcServices	= null;
	private final Log logger = LogFactory.getLog(getClass());
	
	public void initialize() {
		mcServices = MailChimpServiceFactory.getMailChimpServices();
		final String ping = mcServices.ping(apiKey);
		if (IMailChimpServices.PING_SUCCESS.equals(ping)) {
			logger.error("MailChimp connection pinged successfully");
		} else {
			logger.error("Failed to ping MailChimp, response: " + ping);
		}
	}
	
	/*private void listMembers() {
		final Object[] listMembers = mcServices.listMembers(apiKey, listId,IMailChimpServices.STATUS_SUBSCRIBED, "2016-03-30 00:00:00", 0,1000);
		System.out.println("listMembers got " + listMembers.length + " members");
		for (final Object member : listMembers) {
			System.out.print("\tMember: ");
			final Map<String, Object> map = (Map<String, Object>) member;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);

				if (key.equals(IMailChimpServices.MEMBER_FIELD_TIMESTAMP)) {
					try {
						final Date parsedDate = MailChimpUtils.parseDate(value.toString());
						System.out.print(key + "=" + parsedDate + "\t");
					} catch (final ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					System.out.print(key + " = " + value + "("+ value.getClass().getSimpleName() + ")\t");
				}
			}
			System.out.println();
		}
	}*/
	
	/*public void getLists() {
		final Object[] lists = mcServices.lists(apiKey);
		System.out.println("lists:");
		for (final Object list : lists) {
			final Map<String, Object> map = (Map<String, Object>) list;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);
				System.out.print(key + " = " + value + "("+ value.getClass().getSimpleName() + ")\t");
			}
			System.out.println();
		}
	}
	
	private void getDetails() {
		try {
			final Map<String, Object> listMemberInfo = mcServices.listMemberInfo(apiKey, listId,"swapnilsonar999@gmail.com");
			System.out.println("listMemberInfo: ");
			final Map<String, Object> map = listMemberInfo;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);
				System.out.print(key + " = " + value + "("+ value.getClass().getSimpleName() + ")\t");
			}
			System.out.println();

		} catch (final UndeclaredThrowableException e) {
			e.printStackTrace();
		}
	}*/
	
	public void sendCampaign(String subject, String text, int templeteId) {
		final Map<String, Object> options = new HashMap<String, Object>();
		options.put("list_id", listId);
		options.put("subject", subject);
		options.put("from_email", fromEmail);
		options.put("from_name", "Admin");
		options.put("to_name", "test user");
		options.put("template_id", templeteId);//169893
		final Map<String, String> content = new HashMap<String, String>();
		
		final Map<String, String> segmentOpts = new HashMap<String, String>();
		final Map<String, String> typeOpts = new HashMap<String, String>();
		String campaignId = mcServices.campaignCreate(apiKey, "regular", options, content, segmentOpts, typeOpts);
		System.out.println("Campaign Created "+campaignId);
		System.out.println(mcServices.campaignSendNow(apiKey, campaignId));
	}
	
	public void unsubscribe() {
		final Object[] listMembers = getMemberList();
		//System.out.println("listMembers got " + listMembers.length + " members");
		for (final Object member : listMembers) {
			final Map<String, Object> map = (Map<String, Object>) member;
			for (final Object key : map.keySet()) {
				final Object value = map.get(key);
				if (key.equals(IMailChimpServices.MEMBER_FIELD_TIMESTAMP)) {
				} else {
					System.out.print(key + " = " + value + "("+ value.getClass().getSimpleName() + ")\t");
					mcServices.listUnsubscribe(apiKey, listId, value+"", true, true, true);
				}
			}
			System.out.println();
		}
	}
	
	private void addUser(String lastname, String firstname, String email, String guardianId) {
		final Map<String, String> merges = new HashMap<String, String>();
		merges.put("LNAME", lastname);
		merges.put("FNAME", firstname);
		merges.put("TOKEN", redirectUrl+""+guardianServices.getGuardianToken(guardianId));
		merges.put("EMAIL", email);
		merges.put("OPTINIP", "127.0.0.1");
		
		try{
			final boolean listSubscribe = mcServices.listSubscribe(apiKey,
				listId, email, merges,
				IMailChimpServices.EMAIL_TYPE_HTML, false);
			System.out.println("listSubscribe: " + listSubscribe);
		}catch(Exception e){
			System.out.println("Member already present");
		}
		
	}
	
	private Object[] getMemberList() {
		final Object[] listMembers = mcServices.listMembers(apiKey, listId,IMailChimpServices.STATUS_SUBSCRIBED, listStartdate, 0,1000);
		return listMembers;
	}
	
	public void sendEventCreationNotification(List<EventMembersVM> eventMembers) {
		initialize();
		unsubscribe();
		for (EventMembersVM eventMembersVM : eventMembers) {
			addUser(eventMembersVM.getLastname(),eventMembersVM.getFirstname(), eventMembersVM.getEmail(), eventMembersVM.getGuardianId());
		}
		try{
			sendCampaign("New Event Created", "body", Integer.parseInt(eventCreationTemplateId));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public void sendScheduledNotification(LinkedHashMap<String,List<String>> data){
		initialize();
		unsubscribe();
		List<List<String>> list = new ArrayList<List<String>>(data.values());
		for(int i=0; i<data.size(); i++) {
			addUser(list.get(i).get(0), "", list.get(i).get(1), list.get(i).get(2));
		}
		sendCampaign("Your Scheduled Mail", "body", Integer.parseInt(eventCreationTemplateId));
	}
}
