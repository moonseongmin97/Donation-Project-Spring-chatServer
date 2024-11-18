package com.example.demo.donate.donate.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.donate.bank.entity.BankEntity;
import com.example.demo.donate.donate.dto.DonateResponseDto;
import com.example.demo.donate.donate.entity.DonateEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Repository
public class DonateRepository {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
  
    
	// 기부 등록하기
	@Transactional
	public Optional<Long> donateInsert(DonateEntity donateEntity) {
	    entityManager.persist(donateEntity); // 엔티티를 저장
	    // 엔티티가 저장되면서 ID가 자동 할당됩니다.
	    // ID가 null이 아닌지 확인하고 안전하게 Optional로 반환
	    return Optional.ofNullable(donateEntity.getDonationId());
	}
    
 

// 기부 조회하기
    @Transactional
    public Optional<DonateEntity> donateSelect(DonateEntity donateEntity) {
        String jpql = "SELECT m FROM DonateEntity m WHERE m.donationId = :donationId ";
        TypedQuery<DonateEntity> query = entityManager.createQuery(jpql, DonateEntity.class);
        query.setParameter("donationId", donateEntity.getDonationId());
        List<DonateEntity> result = query.getResultList();       
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));            	    	    
    }
    
    @Transactional
    public List<DonateResponseDto> findTop5Donors() throws Exception {
    	
    	LocalDate twoMonthsAgo = LocalDate.now().minusMonths(2);
//    	LocalDateTime startOfDay = twoMonthsAgo.atStartOfDay();
    	
    	String jpql = "SELECT new com.example.demo.donate.donate.dto.DonateResponseDto(d.userName, SUM(d.amount)) " +
                "FROM DonateEntity d " +
                "WHERE d.donationDate >= :twoMonthsAgo " +
                "GROUP BY d.userName,d.userId " +
                "ORDER BY SUM(d.amount) DESC";

    	TypedQuery<DonateResponseDto> query = entityManager.createQuery(jpql, DonateResponseDto.class);
    	query.setParameter("twoMonthsAgo", twoMonthsAgo.atStartOfDay());
    	query.setMaxResults(5);
    	return query.getResultList();

  
    }
    

    
  
    
    	    //  토탈 금액 조회
    	    @Transactional
    	    public Optional<BigDecimal> totalDonate(DonateEntity donateEntity) {
    	    	//String jpql = "select m from DonateEntity m where m.is_cancelled = false";  
    	        String jpql = "SELECT SUM(m.amount) FROM DonateEntity m WHERE m.isCancelled = false";
    	        TypedQuery<BigDecimal> query = entityManager.createQuery(jpql, BigDecimal.class);
    	        BigDecimal totalAmount = query.getSingleResult();
    	        return Optional.ofNullable(totalAmount); // 합계가 null일 경우 Optional로 처리
    	    }
    

	

}
