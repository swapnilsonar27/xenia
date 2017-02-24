package com.mnt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.GroupDao;
import com.mnt.entities.Groups;
import com.mnt.vm.ResponseVM;

@Service
public class GroupServices {
	
	@Autowired
	GroupDao groupDao;
	
	public List<Groups> getGroupByCompany(String id){
		return groupDao.getGroupByCompany(id);
	}
	
	public List<ResponseVM> getGroupNameByEventId(String id){
		return groupDao.getGroupNameByEventId(id);
	}
}
