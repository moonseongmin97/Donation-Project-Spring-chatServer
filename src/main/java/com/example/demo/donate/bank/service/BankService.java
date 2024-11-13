package com.example.demo.donate.bank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.common.exception.CustomException;
import com.example.demo.common.redis.service.RedisServiceImpl;

import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.bank.dto.BankResponseDto;
import com.example.demo.donate.bank.entity.BankEntity;
import com.example.demo.donate.bank.repository.BankRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;

@Service
public class BankService  {

	private static final Exception Exception = null;

	@Autowired  
	private  BankRepository bankRepository;
	
	@Autowired
    private  ModelMapper modelMapper;
	
    @Autowired
    private RedisServiceImpl redisService;
    
    // 모든 회원 조회
    public Map<String,Object> findBankList(BankRequestDto bankReqDto) {
    	Map<String,Object> result = new HashMap<>();
    	try {   			   
	        // 은행 목록 조회
	        List<BankEntity> bankList = bankRepository.findAllBanks();
	        
	        // BankEntity 리스트를 BankResponseDto 리스트로 매핑
	        List<BankResponseDto> bankResponseList = bankList.stream()
	            .map(bank -> modelMapper.map(bank, BankResponseDto.class))
	            .collect(Collectors.toList());    	
	    		    	
	    	result.put("data", bankResponseList);  
            result.put("state", true);
            result.put("msg", "회원 가입 성공.");	  	 	    	
	    	 return result;
    	}catch(Exception e) {
    		e.getStackTrace();
    		e.getMessage();
    		throw new CustomException("은행 목록 조회 중 오류가 발생했습니다.", e);
    	} 	
    }
}
