package com.example.demo.auth.controller;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.dto.MemberDto;
import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.auth.service.MemberService;
import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.response.ApiResponse;
import com.example.demo.common.util.CookieUtil;
import com.example.demo.common.util.JwtProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.PersistenceContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

	@RestController
	@RequestMapping("/api")
	public class MemberController {



		@Autowired
	    private MemberService memberService;
	    
	    
	    @Autowired
	    private RedisServiceImpl redisService;
	    
	    // 모든 회원 조회
	    @GetMapping("/members")
	    public List<MemberEntity> getAllMembers() {
	    	System.out.println("여기까진됨");
	        return memberService.findAllMembers();
	    }

	    // ID로 회원 조회
	    @GetMapping("/{id}")
	    //public Optional<MemberEntity> getMemberById(@PathVariable Long id) {
	    public Optional<MemberEntity> getMemberById(@RequestBody MemberDto member) {
	        //return memberService.findMemberById(member);
	    	return null;
	    }

	    // 이메일로 회원 조회
	    @GetMapping("/email/{email}")
	    public MemberEntity getMemberByEmail(@PathVariable String email) {
	        return memberService.findMemberByEmail(email);
	    }

	    
		 /**
	     * 로그인 상태체크 레디스 토큰 확인
	     * 설명 -사용자 토큰 값이 만료되었는지 체크(레디스)
	     *
	     * @param memberDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 로그인 정보 체크 후 사용자 정보 및 메시지를 담은 Map 객체
	     */	
	    @PostMapping("/loginCheck")
	    public ResponseEntity select(@RequestBody  MemberRequestDto memberDto , @RequestBody  MemberResponseDto member ,HttpServletRequest request,  HttpServletResponse res) throws Exception {
	    	try {
    		Cookie[] cookies  = request.getCookies();		
    		String uuid ;
    		HttpStatus httpStatus ;   		
    		boolean jwtCheck = CookieUtil.getCookieValue(request, "jwtToken").isEmpty(); // 쿠키 안에 토큰 값 확인
    		
    		if(!jwtCheck) {
    			uuid= CookieUtil.getCookieValue(request, "jwtToken").get();
        		memberDto.setUuid(uuid);
        		memberDto.setIpAddress("");	
    		}
    		
    		Map<String, Object> result =memberService.findSessionMember(memberDto);
    		httpStatus = ((boolean) result.get("state")) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST ;
    		if(result.get("jwt")!=null) {
    			res.addCookie((Cookie) result.get("jwt"));	
    		}		    	
    		
	        ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );	        
	        
	        return ResponseEntity.status(httpStatus).body(response);
	        
	    	

	    	}catch(Exception e) {
	    		e.getStackTrace();
	            ApiResponse response = new ApiResponse(false, "회원 체크 실패");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);	            
	    	}
	    }
	    
		 /**
	     * 로그아웃 컨트롤러
	     * 설명- 로그아웃시 레디스 토큰 삭제 쿠키(만료값) 정보 반환합니다.
	     * @param memberDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 로그인 정보 체크 후 사용자 정보 및 메시지를 담은 Map 객체
	     */	
	    @PostMapping("/logout")
	    public ResponseEntity logout(@RequestBody  MemberRequestDto memberDto ,HttpServletRequest request,  HttpServletResponse res) throws Exception {
    		System.out.println("로그아웃 logout ======");
    		Cookie[] cookies  = request.getCookies();
    		boolean jwtCheck = CookieUtil.getCookieValue(request, "jwtToken").isEmpty(); // 쿠키 안에 토큰 값 확인
    		String uuid= null;
    		if(!jwtCheck) {
    			uuid= CookieUtil.getCookieValue(request, "jwtToken").get();
        		memberDto.setUuid(uuid);
        		memberDto.setIpAddress("");	
    		}else {
    			
    			
    		}
   
    		//System.out.println("회원조회 컨트롤러 - 레디스 조회값======"+redisService.getUserIdFromToken(uuid));
	    	Map<String, Object> result =memberService.logoutMember(memberDto);
	    	
	    	  Cookie jwtCookie = new Cookie("jwtToken", null); // 토큰 이름 지정
	    	    jwtCookie.setHttpOnly(true);
	    	    jwtCookie.setMaxAge(0); // 만료 시간 0으로 설정
	    	    jwtCookie.setPath("/"); // 쿠키 경로 설정

	    	    res.addCookie(jwtCookie); // 응답에 쿠키 추가
	    	
	    	ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );	    	
	    	return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	    	
	    	
	    	
	    }
	    

	    
		
		 /**
	     * 로그인 컨트롤러
	     * 설명 - 로그인 시 회원 체크 후 정보 반환합니다.
	     *
	     * @param memberDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 로그인 정보 체크 후 사용자 정보 및 메시지를 담은 Map 객체
	     */	
	    @PostMapping(value = "/signIn")
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
	    		

		    	Map<String, Object> result =memberService.findActiveMemberByLoginId(memberDto); 			    		
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
	    
		 /**
	     * 회원가입 컨트롤러
	     * 설명 - 회원 중복체크 후 회원 가입
	     *
	     * @param memberDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 로그인 정보 체크 후 사용자 정보 및 메시지를 담은 Map 객체
	     */	
	    @PostMapping(value = "/signUp")
	    public ResponseEntity joinMember(@RequestBody  MemberRequestDto memberDto) {
	    	try {
		    	Map<String, Object> result =memberService.joinMember(memberDto); 			    		
	            ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        } catch (Exception e) {
	            ApiResponse response = new ApiResponse(false, "서버 에러로 회원 가입 실패");
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);	            
	        }
	    }
	    
	    
	}
