package com.mnt.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.entities.Inventories;
import com.mnt.entities.PaymentDetails;
import com.mnt.entities.Payments;

@Repository
@Transactional
@Component("paymentDao")
public class PaymentDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	EventDao eventDao;
	
	public void savePaymentInfo(String token, Double amount, Integer eventUserId, String paidUsers) {/*
		Session session = sessionFactory.getCurrentSession();
		Payments pay = new Payments();
		pay.paymentToken = token;
		pay.amount = amount;
		EventUsers eu = (EventUsers) session.get(EventUsers.class, Integer.parseInt(eventUserId+""));
		pay.eventUser = eu;
		session.save(pay);
		
		
		String[] paidList = paidUsers.split(",");
		for(int i=0; i<paidList.length; i++) {
			String currentUserDetails = paidList[i]; 
			String[] userId = currentUserDetails.split("#");
			if(userId.length == 0){
				eventDao.updateUserPaidStatusById(paidList[i], true);
			}else {
				eventDao.updateUserPaidStatusById(userId[0], true);
				for(int j=1; j<userId.length; j++) {
						System.out.println(pay.paymentId+" and  "+ userId[j]);
						createUserPaidInventoryById(pay.paymentId+"", userId[j], userId[0]);
				}
			}
			System.out.println(paidList[i]);
		}
	*/}
	
//	public void createUserPaidInventoryById(String payId, String inventoryId, String eventUserId) {
//		Session session = sessionFactory.getCurrentSession();
//		PaymentDetails pay = new PaymentDetails();
//		
//		Payments payment = (Payments) session.get(Payments.class, Integer.parseInt(payId+""));
//		pay.payment = payment;
//		
//		Inventories inventory = (Inventories) session.get(Inventories.class, Integer.parseInt(inventoryId+""));
//		pay.inventory = inventory;
//		
//		EventUsers eventUser = (EventUsers) session.get(EventUsers.class, Integer.parseInt(eventUserId+""));
//		pay.eventUser= eventUser;
//		
//		session.save(pay);
//		
//	}
}
