package com.example.demo.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.auth.entity.MemberEntity;

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
public class MemberRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	

	
	    
	    // 모든 회원 조회
	    public List<MemberEntity> findAll() {
	        //String jpql = "SELECT m FROM Member m";
	        //TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
	     	System.out.println("세번쨰도 됨");
	     	//entityManager.
	     	
	     	List<MemberEntity> a = entityManager.createQuery("SELECT m FROM MemberEntity m", MemberEntity.class).getResultList();
	     	//System.out.println("짠"+entityManager.createQuery("SELECT m FROM MemberEntity m", MemberEntity.class).getResultList().getClass().getName());	     
	     	
	     	for (MemberEntity member : a) {
	     	    Map<String, Object> memberMap = new HashMap<>();
	     	    memberMap.put("ID", member.getUsername());
	     	    memberMap.put("Username", member.getUsername());
	     	    memberMap.put("Email", member.getEmail());
	     	    memberMap.put("CreatedAt", member.getCreatedAt());

	     	    System.out.println(memberMap);  // Map 형태로 출력
	     	}
	     	
	     	
	     	
	     	return entityManager.createQuery("SELECT m FROM MemberEntity m", MemberEntity.class).getResultList();
	        //return entityManager.createQuery("SELECT m FROM Member m", MemberEntity.class).getResultList();
	        //return query.getResultList();
	    }

	    // ID로 회원 조회
	    public Optional<MemberEntity> findById(Long id) {
	    	MemberEntity member = entityManager.find(MemberEntity.class, id);
	    	System.out.println("레파지토리 탐");
	        return Optional.ofNullable(member);
	    }
	    
	    // 로그인 ID로 활성화된 회원 조회
	    public Optional<MemberEntity> findActiveMemberByLoginId2(MemberEntity loginId) {
	    	System.out.println("레파지토리findActiveMemberByLoginId탐===");
	        String jpql = "SELECT m FROM MemberEntity m WHERE m.loginId = :loginId";
	        TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
	        query.setParameter("loginId", loginId);
	        List<MemberEntity> result = query.getResultList();
	        
	        System.out.println("레파지토리 result==");
	        System.out.println(result.isEmpty() ? Optional.empty() : Optional.of(result.get(0).getUsername()));
	        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	    }
	    
	 // 로그인 ID와 비밀번호로 활성화된 회원 조회
	    public Optional<MemberEntity> findActiveMemberByLoginId(MemberEntity memberEntity) {
	        System.out.println("레파지토리 findActiveMemberByLoginIdAndPassword 탐 ===");
	        
	        String jpql = "SELECT m FROM MemberEntity m WHERE m.loginId = :loginId AND m.passwordHash = :passwordHash AND m.status = 'ACTIVE'";
	        TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
	        query.setParameter("loginId", memberEntity.getLoginId());
	        query.setParameter("passwordHash", memberEntity.getPasswordHash());
	        
	        List<MemberEntity> result = query.getResultList();
	        
	        System.out.println("레파지토리 result=="+result);
	        System.out.println("레파지토리 비번=="+memberEntity.getPasswordHash());
	        System.out.println(result.isEmpty() ? "No results found" : "Found member: " + result.get(0).getUsername());
	        
	        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
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
