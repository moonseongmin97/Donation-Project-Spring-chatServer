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
import com.example.demo.common.response.ApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

	@RestController
	@RequestMapping("/api")
	public class MemberController {

	    @Autowired
	    private MemberService memberService;

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
	    
	    
	    // 회원 조회
	    @PostMapping(value = "/signIn")
	    public ResponseEntity selectMember(@RequestBody  MemberRequestDto memberDto) {
	    	try {
		    	Map<String, Object> result =memberService.findActiveMemberByLoginId(memberDto); 			    		
		    	ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data") );		    	
		    		return ResponseEntity.status(HttpStatus.CREATED).body(response);	            
	        } catch (Exception e) {
	            ApiResponse response = new ApiResponse(false, "서버 에러로 회원 가입 실패");
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
