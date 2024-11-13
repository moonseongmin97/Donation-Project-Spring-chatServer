package com.example.demo.donate.donate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.donate.donate.dto.DonateRequestDto;
import com.example.demo.donate.donate.dto.DonateResponseDto;
import com.example.demo.donate.donate.entity.DonateEntity;
import com.example.demo.donate.donate.repository.DonateRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DonateService  {

    @Autowired
    private  ModelMapper modelMapper;
    
    @Autowired
    private DonateRepository  donateRepository;
    
    // 기부 등록 하기
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
    		
    	}
   	
        return result;
    }

}
