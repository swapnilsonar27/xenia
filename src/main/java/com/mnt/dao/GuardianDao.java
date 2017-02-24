package com.mnt.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.entities.Guardians;

@Repository
@Transactional
@Component("guardianDao")
public class GuardianDao {

	@Autowired
	SessionFactory sessionFactory;
	
	public String getGuardianTokenById(String id){
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT g FROM Guardians g WHERE g.guardianId = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", Integer.parseInt(id));
		Guardians guardian = (Guardians) query.uniqueResult();
		
		return guardian.token;
	}
}
