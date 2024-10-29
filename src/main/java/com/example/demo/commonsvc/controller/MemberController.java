package com.example.demo.commonsvc.controller;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.commonsvc.service.MemberService;
import com.example.demo.commonsvc.to.MemberEntity;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceContext;

	@RestController
	@RequestMapping("/api/members")
	public class MemberController {

	    @Autowired
	    private MemberService memberService;

	    // 모든 회원 조회
	    @GetMapping
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
	    @PostMapping
	    public void createMember(@RequestBody MemberEntity member) {
	        memberService.saveMember(member);
	    }
	}
