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
	    
	    
	 // 로그인시 회원 조회
	    public Optional<MemberEntity> findActiveMemberByLoginId(MemberEntity memberEntity) throws Exception{
		        String jpql = "SELECT m FROM MemberEntity m WHERE m.loginId = :loginId AND m.status = 'ACTIVE'";
		        TypedQuery<MemberEntity> query = entityManager.createQuery(jpql, MemberEntity.class);
		        query.setParameter("loginId", memberEntity.getLoginId());
		        List<MemberEntity> result = query.getResultList();       
		        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	    }
	    
	    // 회원가입 시 ID 중복 체크용으로 사용할 메서드
	    public boolean existsByLoginId(String loginId) throws Exception {	    		 
	        String jpql = "SELECT COUNT(m) FROM MemberEntity m WHERE m.loginId = :loginId";
	        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
	        query.setParameter("loginId", loginId);
	        
	        Long count = query.getSingleResult();
	        return count > 0;
	    }
	    
		 // 회원 가입
	    @Transactional
	    public void joinMember(MemberEntity memberEntity) {
	            entityManager.persist(memberEntity);
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
