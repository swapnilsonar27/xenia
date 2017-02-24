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

import com.mnt.entities.Companies;
import com.mnt.entities.auth.AuthUser;
import com.mnt.vm.ResponseVM;
import com.mnt.vm.UserVM;

@Repository
@Transactional
@Component("userDao")
public class UserDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<ResponseVM> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from AuthUser");
		List<AuthUser> user = selectQuery.list();
		List<ResponseVM> userList= new ArrayList<ResponseVM>();
		for (AuthUser authUser : user) {
			ResponseVM vm = new ResponseVM();
			vm.setId(Integer.parseInt(authUser.getId()+""));
			vm.setLabel(authUser.getName()+"");
			userList.add(vm);
		}
		
		return userList;
	}

	/*public List<UserVM> getAllUserDetails() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from AuthUser");
		List<AuthUser> user = selectQuery.list();
		List<UserVM> userList= new ArrayList<UserVM>();
		for (AuthUser authUser : user) {
			UserVM vm = new UserVM();
			vm.setAuth_id(authUser.getId()+"");
			vm.setName(authUser.getName()+"");
			vm.setUsername(authUser.getUsername());
			vm.setEmail_id(authUser.getEmail());
			try{
				vm.setParent_id(authUser.getParent().getId()+"");
				vm.setParent_name(authUser.getParent().getName());
			}catch(NullPointerException e){
				vm.setParent_id(authUser.getId()+"");
				vm.setParent_name(authUser.getName());
			}
			userList.add(vm);
		}
		return userList;
	}
	
	public List<ResponseVM> getUserNameById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("Select au from AuthUser au, EventUsers eu where eu.event.eventId=:eventId and eu.user.id=au.id");
		selectQuery.setParameter("eventId", Integer.parseInt(id));
		List<AuthUser> user = selectQuery.list();
		List<ResponseVM> userList= new ArrayList<ResponseVM>();
		for (AuthUser auth : user) {
			ResponseVM vm = new ResponseVM();
			vm.setId(auth.getId()+"");
			vm.setLabel(auth.getName()+"");
			userList.add(vm);
		}
		
		return userList;
	}
	
	public void saveUser(UserVM vm) {
		Session session = sessionFactory.getCurrentSession();
		AuthUser user = new AuthUser();
		user.setName(vm.getName());
		user.setUsername(vm.getUsername());
		user.setEmail(vm.getEmail_id());
		user.setEnabled(true);
		AuthUser auth = (AuthUser) session.get(AuthUser.class, Long.parseLong(vm.getParent_id()));
		user.setParent(auth);
		session.save(user);
	}*/
}
