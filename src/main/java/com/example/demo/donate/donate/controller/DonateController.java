package com.example.demo.donate.donate.controller;	
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
import com.example.demo.donate.donate.dto.DonateRequestDto;
import com.example.demo.donate.donate.service.DonateService;

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
	public class DonateController {

		
		@Autowired
		DonateService donateService; 
		
	    // ID로 회원 조회
	    //@GetMapping("/bankList")	 
	    public Optional<MemberEntity> getBank(@RequestBody DonateRequestDto danate) {

	    	
	    	
	    	return null;
	    }
	    
	    
	}
