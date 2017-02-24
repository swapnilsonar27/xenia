package com.mnt;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
public class XeniaApplication {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(XeniaApplication.class, args);
		System.out.println("here");
	}	
	
	@Bean(name="sessionFactory")
    public SessionFactory sessionFactory() {
        return ((HibernateEntityManagerFactory) this.entityManagerFactory).getSessionFactory();
    }
	
}
