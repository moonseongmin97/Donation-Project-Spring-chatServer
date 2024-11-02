package com.example.demo.auth.controller;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.auth.dto.MemberDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.auth.service.MemberService;
import com.example.demo.common.response.ApiResponse;

import java.util.List;
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
	    public Optional<MemberEntity> getMemberById(@PathVariable Long id) {
	        return memberService.findMemberById(id);
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
	    public void select(HttpServletRequest request,   @RequestBody MemberEntity member) throws Exception {
	        memberService.saveMember(member);
	    }
	    
	    
	    // 회원 가입
	    //@PostMapping("/signup")
	    @PostMapping(value = "/signup")
	    public ResponseEntity joinMember(@RequestBody  MemberDto memberDto) {
	    	System.out.println("실행=="+memberDto.getId());
	    	
	    	try {
	            //memberService.saveMember(memberDto);
	        	
	        	System.out.println("성공");
	            ApiResponse response = new ApiResponse(true, "회원 가입 성공");
	            
	            return new ResponseEntity<>(response, HttpStatus.CREATED);
	            //return ResponseEntity.status(HttpStatus.CREATED).body(response);
	           //return memberDto;
	        } catch (Exception e) {
	            ApiResponse response = new ApiResponse(false, "회원 가입 실패: " + e.getMessage());
	            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	            //return ResponseEntity.status(HttpStatus.CREATED).body(response);
	            
	            //return memberDto;
	        }
	    }
	    
	    
	}
