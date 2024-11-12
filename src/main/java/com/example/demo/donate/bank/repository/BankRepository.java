package com.example.demo.donate.bank.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.donate.bank.entity.BankEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public class BankRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
    // 모든 은행 리스트 조회
    public List<BankEntity> findAllBanks() {
        String jpql = "SELECT b FROM BankEntity b";
        TypedQuery<BankEntity> query = entityManager.createQuery(jpql, BankEntity.class);
        return query.getResultList();
    }

    // 은행 ID로 특정 은행 조회
    public BankEntity findById(Long id) {
        return entityManager.find(BankEntity.class, id);
    }

    // 은행 추가 (예시로 새로운 은행을 추가)
    public void save(BankEntity bankEntity) {
        entityManager.persist(bankEntity);
    }
	    
	 

	

}
