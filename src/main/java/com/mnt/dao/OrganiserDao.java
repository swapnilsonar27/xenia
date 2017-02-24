package com.mnt.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.entities.CompanyOrganisers;

@Repository
@Transactional
@Component("organiserDao")
public class OrganiserDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<CompanyOrganisers> getOrganiserByCompany(String id) {
		Session session = sessionFactory.getCurrentSession();
		Query selectQuery = session.createQuery("from CompanyOrganisers where company.companyId = :id");
		selectQuery.setParameter("id", Integer.parseInt(id));
		List<CompanyOrganisers> groups = selectQuery.list();
		return groups;
	}
	
}
