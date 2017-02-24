package com.mnt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.UserDao;
import com.mnt.vm.ResponseVM;
import com.mnt.vm.UserVM;

@Service
public class UserServices {

	@Autowired
	UserDao userDao;
	
	public List<ResponseVM> getUsers() {
		return userDao.getUsers();
	}
	
	/*public List<UserVM> getAllUserDetails() {
		return userDao.getAllUserDetails();
	}
	
	public List<ResponseVM> getUserNameById(String id) {
		return userDao.getUserNameById(id);
	}
	
	public void saveUser(UserVM vm){
		userDao.saveUser(vm);
	}*/
}
