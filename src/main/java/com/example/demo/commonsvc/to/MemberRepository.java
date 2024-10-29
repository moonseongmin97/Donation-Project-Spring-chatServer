package com.example.demo.commonsvc.to;

import org.springframework.stereotype.Repository;




import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	

	
	    
	    // 모든 회원 조회
	    public List<MemberEntity> findAll() {
	        //String jpql = "SELECT m FROM Member m";
	        //TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
	     	System.out.println("세번쨰도 됨");
	     	//entityManager.
	        return entityManager.createQuery("SELECT m FROM Member m", MemberEntity.class).getResultList();
	        //return query.getResultList();
	    }

	    // ID로 회원 조회
	    public Optional<MemberEntity> findById(Long id) {
	    	MemberEntity member = entityManager.find(MemberEntity.class, id);
	        return Optional.ofNullable(member);
	    }

	    // 이메일로 회원 조회
	    public MemberEntity findByEmail(String email) {
	        String jpql = "SELECT m FROM Member m WHERE m.email = :email";
	        TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
	        query.setParameter("email", email);
	        return query.getSingleResult();
	    }

	    // 회원 저장
	    public void save(MemberEntity member) {
	        entityManager.persist(member);
	    }
	

}
