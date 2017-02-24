package com.mnt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.OrganiserDao;
import com.mnt.entities.CompanyOrganisers;

@Service
public class OrganiserServices {
	@Autowired
	OrganiserDao organiserDao;

	public List<CompanyOrganisers> getOrganiserByCompany(String id){
		return organiserDao.getOrganiserByCompany(id);
	}
	
	
}
