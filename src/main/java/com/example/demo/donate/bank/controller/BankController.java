package com.example.demo.donate.bank.controller;	
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.CookieUtil;
import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.bank.entity.BankEntity;
import com.example.demo.donate.bank.service.BankService;


	@RestController
	@RequestMapping("/api")
	public class BankController {

		
		@Autowired
		BankService bankService; 
		
		@GetMapping("/bankList")
		public ResponseEntity getBank(@RequestParam Long bankId) {
			BankRequestDto bankReqDto= new BankRequestDto();
			Map<String,Object> result = bankService.findBankList(bankReqDto);
			ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);					
		}
		
		@GetMapping("/insert")
		public ResponseEntity getBank2(@RequestParam Long bankId , HttpServletRequest request , HttpServletResponse res) {
			BankRequestDto bankReqDto= new BankRequestDto();
			bankReqDto.setBankName(bankId.toString());
			Map<String,Object> result = bankService.findBankList(bankReqDto); // memberService.joinMember(memberDto);						
			
			ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );
			
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		    // 로직 처리
		}
		
		
	    // 회원 조회
	    //@PostMapping(value = "/signIn")
	    public ResponseEntity selectMember(@RequestBody  MemberRequestDto memberDto , HttpServletRequest request , HttpServletResponse res) {
	    	try {
	    		System.out.println("회원조회 컨트롤러 signIn======");
	    		Cookie[] cookies  = request.getCookies();	
	    		 //1 쿠키 값은 있다  
  	    		// 2. 쿠키 값 안에 토큰 값도 있다.
	    		
	    		boolean jwtCheck = CookieUtil.getCookieValue(request, "jwtToken").isEmpty(); // 쿠키 안에 토큰 값 확인	    		
	    		if(cookies != null && cookies.length > 0 && !jwtCheck) {
		    		String uuid=  CookieUtil.getCookieValue(request, "jwtToken").get();
		    		memberDto.setUuid(uuid);
		    		memberDto.setIpAddress("");	    
		    		//System.out.println("회원조회 컨트롤러 - 레디스 조회값======"+redisService.getUserIdFromToken(uuid));	    			
	    		}
	    		

		    	Map<String, Object> result = null; //memberService.findActiveMemberByLoginId(memberDto); 			    		
		    	ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );
		    	
		    	if((boolean)result.get("state")) {
		    		if(result.get("jwt")!=null) {
		    			res.addCookie((Cookie) result.get("jwt"));	
		    		}		    		
		    	}
		    	return ResponseEntity.status(HttpStatus.CREATED).body(response);	            
	        } catch (Exception e) {
	            ApiResponse response = new ApiResponse(false, "서버 에러로 회원 가입 실패");
		           System.err.println("오류문 =="+e.getMessage());
		           e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);	            
	        }
	    }
		
		
		
		
	    // ID로 회원 조회
	    //@GetMapping("/bankList")	 
	    public Map<String, Object> getBank2(@RequestBody BankRequestDto bankReqDto ) {
	    	
	    	//Map<String, Object> result = bankService.findBankList(bankReqDto); // memberService.joinMember(memberDto);
	    	
	    	
	    	return null; //result;
	    }
	    
	    
	}
