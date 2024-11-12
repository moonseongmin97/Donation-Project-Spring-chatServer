package com.example.demo.donate.bank.controller;	
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.bank.service.BankService;


	@RestController
	@RequestMapping("/api")
	public class BankController {

		
		@Autowired
		BankService bankService; 
		
		@GetMapping("/bankList")
		public BankRequestDto getBank(@RequestParam Long bankId) {
			BankRequestDto bankReqDto= new BankRequestDto();
			bankReqDto.setBankName(bankId.toString());
			BankRequestDto result = bankService.findBankList(bankReqDto); // memberService.joinMember(memberDto);
			return result;
		    // 로직 처리
		}
		
		
	    // ID로 회원 조회
	    //@GetMapping("/bankList")	 
	    public Map<String, Object> getBank2(@RequestBody BankRequestDto bankReqDto ) {
	    	
	    	//Map<String, Object> result = bankService.findBankList(bankReqDto); // memberService.joinMember(memberDto);
	    	
	    	
	    	return null; //result;
	    }
	    
	    
	}
