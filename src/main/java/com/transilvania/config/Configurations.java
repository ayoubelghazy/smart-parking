package com.transilvania.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.PercentFormatter;

@Configuration
public class Configurations {

	@Bean
	public EntityManager getEM(){
		EntityManagerFactory emf=Persistence.createEntityManagerFactory("SmartCity");
		EntityManager em=emf.createEntityManager();
		return em;
	}
}
