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
import com.mnt.vm.ResponseVM;

@Repository
@Transactional
@Component("companyDao")
public class CompanyDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public List<ResponseVM> getCompanies() {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from Companies");
		List<Companies> companies = selectQuery.list();
		List<ResponseVM> compList= new ArrayList<ResponseVM>();
		for (Companies company: companies) {
			ResponseVM vm = new ResponseVM();
			vm.setId(company.companyId);
			vm.setLabel(company.companyName+"");
			compList.add(vm);
		}
		
		return compList;
	}
}
