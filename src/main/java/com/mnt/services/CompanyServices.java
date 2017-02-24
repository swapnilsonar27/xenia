package com.mnt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.CompanyDao;
import com.mnt.vm.ResponseVM;

@Service
public class CompanyServices {

	@Autowired
	CompanyDao companyDao;
	public List<ResponseVM> getAllCompanies(){
		return companyDao.getCompanies();
	}
}
