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

import com.mnt.entities.EventGroups;
import com.mnt.entities.Groups;
import com.mnt.vm.ResponseVM;

@Repository
@Transactional
@Component("groupDao")
public class GroupDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<Groups> getGroupByCompany(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from Groups where company.companyId = :id");
		selectQuery.setParameter("id", Integer.parseInt(id));
		List<Groups> groups = selectQuery.list();
		return groups;
	}
	
	public List<ResponseVM> getGroupNameByEventId(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from EventGroups where event.eventId=:id");
		selectQuery.setParameter("id", Integer.parseInt(id));
		List<EventGroups> user = selectQuery.list();
		List<ResponseVM> userList= new ArrayList<ResponseVM>();
		for (EventGroups group : user) {
			ResponseVM vm = new ResponseVM();
			vm.setId(group.eventGroupId);
			vm.setLabel(group.group.name+"");
			userList.add(vm);
		}
		
		return userList;
	}
	
}
