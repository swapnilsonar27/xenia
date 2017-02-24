package com.mnt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnt.dao.PaymentDao;

@Service
public class PaymentServices {

	@Autowired
	PaymentDao paymentDao;
	
	public void savePaymentInfo(String token, Double amount, Integer eventUserId, String paidUsers) {
		paymentDao.savePaymentInfo(token, amount, eventUserId, paidUsers);
	}
}
