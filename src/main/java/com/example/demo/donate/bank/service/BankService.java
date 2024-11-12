package com.example.demo.donate.bank.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



import com.example.demo.common.redis.service.RedisServiceImpl;

import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.bank.entity.BankEntity;
import com.example.demo.donate.bank.repository.BankRepository;
import java.util.List;
import java.util.Map;

@Service
public class BankService  {

	@Autowired  
	private  BankRepository bankRepository;
	
	@Autowired
    private  ModelMapper modelMapper;
	
    @Autowired
    private RedisServiceImpl redisService;
    
    // 모든 회원 조회
    public BankRequestDto findBankList(BankRequestDto bankReqDto) {
    	//BankEntity bankEntity = modelMapper.map(bankReqDto, BankEntity.class);  //MemberRequestDto -> MemberEntity
    	
    	List<BankEntity> a= bankRepository.findAllBanks();
    	
    	bankReqDto.setBankName(a.toString());
    	
    	
        return bankReqDto;
    }

}
