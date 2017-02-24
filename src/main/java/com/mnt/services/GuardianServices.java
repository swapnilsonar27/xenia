package com.mnt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.GuardianDao;

@Service
public class GuardianServices {

	@Autowired
	GuardianDao guardianDao;
	
	public String getGuardianToken(String guardianId){
		return guardianDao.getGuardianTokenById(guardianId);
	}
}
