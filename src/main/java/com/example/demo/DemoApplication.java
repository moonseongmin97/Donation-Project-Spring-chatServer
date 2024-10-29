package com.example.demo;

import com.example.demo.commonsvc.to.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}
	/*
	 * @Autowired public static String findMember2() { EntityManagerFactory emf =
	 * Persistence.createEntityManagerFactory("hello2");
	 * 
	 * EntityManager em = emf.createEntityManager();
	 * 
	 * EntityTransaction tx = em.getTransaction(); tx.begin();
	 * 
	 * 
	 * MemberEntity FindMember = null; try {
	 * 
	 * 
	 * FindMember = em.find(MemberEntity.class, 1L); System.out.println("id= " +
	 * FindMember.getId()); System.out.println("name= " + FindMember.getName());
	 * System.out.println("class= " + FindMember.getClass());
	 * 
	 * FindMember.setName("updateReact?");
	 * 
	 * 
	 * // em.persist();
	 * 
	 * tx.commit();
	 * 
	 * } catch (Exception e) { tx.rollback(); } finally { em.close(); emf.close(); }
	 * return FindMember.getName(); };
	 */

}
