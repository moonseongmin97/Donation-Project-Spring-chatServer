package com.example.demo.donate.donate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.common.exception.CustomException;
import com.example.demo.donate.donate.dto.DonateRequestDto;
import com.example.demo.donate.donate.dto.DonateResponseDto;
import com.example.demo.donate.donate.entity.DonateEntity;
import com.example.demo.donate.donate.repository.DonateRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DonateService  {

    @Autowired
    private  ModelMapper modelMapper;
    
    @Autowired
    private DonateRepository  donateRepository;


    
    
    /**
     * [메서드 설명]
     * 예: 기부 등록 후 조회 값 반환
     *
     * @param donateRequestDto 기부 요청 정보가 담긴 DTO
     * @param req HttpServletRequest 요청 객체
     * @param res HttpServletResponse 응답 객체
     * @return 등록된 기부 정보 및 상태 메시지를 담은 Map 객체
     * @throws  특정 예외 상황 발생 시 설명
     */
    public Map<String,Object> donateInsert(DonateRequestDto donateRequestDto) {
    	Map<String,Object> result = new HashMap<>();
    	//1 requestDto -> entity 변환
    	// 인서트
    	//2 entity -> responseDto
    	// 성공했을때 리절트에 성공 주기
    	
    	try {    		
    		DonateEntity donateEntity = modelMapper.map(donateRequestDto, DonateEntity.class);  //DonateRequestDto -> DonateEntity    		
    		donateRepository.donateInsert(donateEntity); // 기부 등록    		
    		Optional<DonateEntity> response =donateRepository.donateSelect(donateEntity); //기부 조회   		
	        
	        if (response.isEmpty()) {  
	            result.put("state", false);
	            result.put("msg", "등록 중 오류 발생(기부 상세 조회 불가)");
	        }else {
	        	DonateResponseDto responseDto = modelMapper.map(response.get(), DonateResponseDto.class);  // DonateEntity -> DonateResponseDto	 
			      result.put("state", true);
		          result.put("msg", "기부 등록 성공");
		          result.put("data", responseDto);	        	
	        } 
    		
    	}catch(Exception e){   		
    		e.getStackTrace();
    		e.getMessage();
    		throw new CustomException("기부금 조회 중 오류가 발생했습니다.", e);
    	}
   	
        return result;
    }
    
    
    /**
     * [메서드 설명]
     * 예: 전체 기부 금액을 조회하여 반환합니다.
     *
     * @param donateRequestDto 기부 요청 정보가 담긴 DTO
     * @param req HttpServletRequest 요청 객체
     * @param res HttpServletResponse 응답 객체
     * @return 총 기부 금액 및 상태 메시지를 담은 Map 객체
     * @throws SomeException 특정 예외 상황 발생 시 설명
     */
    public Map<String,Object> donateTotal(DonateRequestDto donateRequestDto) {
    	Map<String,Object> result = new HashMap<>();
    	//1 requestDto -> entity 변환
    	// 인서트
    	//2 entity -> responseDto
    	// 성공했을때 리절트에 성공 주기
    	
    	try {    		
    		DonateEntity donateEntity = modelMapper.map(donateRequestDto, DonateEntity.class);  //DonateRequestDto -> DonateEntity    		
    		//donateRepository.donateInsert(donateEntity); // 기부 등록    		
    		Optional<BigDecimal> response ;
    		
    		if(false) { //레디스에 기부금 키값 체크
    			response = null;    		  		//레디스에서 기부금토탈 조회 값 
    		}else {
    			response = donateRepository.totalDonate(donateEntity); //기부 db 조회
    			//레디스 값 셋
    		}
    		
	        if (response.isEmpty()) {
	            result.put("state", false);
	            result.put("msg", "기부금 조회 값이 없음");
	        }else {
	        	DonateResponseDto responseDto = modelMapper.map(response.get(), DonateResponseDto.class);  // DonateEntity -> DonateResponseDto
	        	responseDto.setTotalAmount(response.get());
			      result.put("state", true);
		          result.put("msg", "총 기부금 조회 성공");
		          result.put("data", responseDto);	        	
	        } 
    		
    	}catch(Exception e){   		
    		e.getStackTrace();
    		e.getMessage();
    		throw new CustomException("기부금 조회 중 오류가 발생했습니다.", e);
    	}
   	
        return result;
    }
    
    
    

}
