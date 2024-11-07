package com.example.demo.auth.controller;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.dto.MemberDto;
import com.example.demo.auth.dto.MemberRequestDto;
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

	    // 회원 저장
	    @PostMapping ("/join")
	    public void createMember(@RequestBody MemberEntity member) {
	        memberService.saveMember(member);
	    }
	    
	    // 회원 조회
	    @PostMapping("/select")
	    public void select(HttpServletRequest request,   @RequestBody MemberDto member) throws Exception {
	        //memberService.findMemberById(member);
	    }
	    
	    // 로그 아웃
	    @PostMapping("/logout")
	    public ResponseEntity logout(@RequestBody  MemberRequestDto memberDto ,HttpServletRequest request,  HttpServletResponse res) throws Exception {
    		System.out.println("로그아웃 logout ======");
    		Cookie[] cookies  = request.getCookies();	    			    		
    		String uuid=CookieUtil.getCookieValue(request, "jwtToken").get();		
    		memberDto.setUuid(uuid);
    		memberDto.setIpAddress("");	
    		
    		if (uuid == null) {
    			ApiResponse response = new ApiResponse(true,"로그아웃 성공");
    		    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    		}
    		
    		//System.out.println("회원조회 컨트롤러 - 레디스 조회값======"+redisService.getUserIdFromToken(uuid));
	    	Map<String, Object> result =memberService.findActiveMemberByLoginId(memberDto); 			    		
	    	ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );
	    	
	    	if((boolean)result.get("state")) {
	    		if(result.get("jwt")!=null) {
	    			res.addCookie((Cookie) result.get("jwt"));	
	    		}		    		
	    	}
	    	return ResponseEntity.status(HttpStatus.CREATED).body(response);	
	    	
	    	
	        //memberService.findMemberById(member);
	    	
	    	
	    }
	    

	    
	    
	    // 회원 조회
	    @PostMapping(value = "/signIn")
	    public ResponseEntity selectMember(@RequestBody  MemberRequestDto memberDto , HttpServletRequest request , HttpServletResponse res) {
	    	try {
	    		System.out.println("회원조회 컨트롤러 signIn======");
	    		Cookie[] cookies  = request.getCookies();	    			    		
	    		String uuid=  null;// CookieUtil.getCookieValue(request, "jwtToken").get();
	    		// 여기 안에서가 쿠키값 비었을떄 문제임  원인 분석하자 System.out.println("값체크=="+CookieUtil.getCookieValue(request, "jwtToken").get());
	    		memberDto.setUuid(uuid);
	    		memberDto.setIpAddress("");	    
	    		//System.out.println("회원조회 컨트롤러 - 레디스 조회값======"+redisService.getUserIdFromToken(uuid));
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
	    
	    // 회원 가입
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
