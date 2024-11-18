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
import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.donate.dto.DonateRequestDto;
import com.example.demo.donate.donate.service.DonateService;

import antlr.Parser;

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
		
		
		
		 /**
	     * 기부 등록 후 기부 정보 반환합니다.
	     *
	     * @param donateRequestDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 등록된 기부 정보 및 메시지를 담은 Map 객체
	     */		
		@PostMapping("/donateInsert")
		public ResponseEntity donateInsert(@RequestBody DonateRequestDto donateRequestDto,MemberRequestDto memberDto ,HttpServletRequest req,  HttpServletResponse res) {						
			donateRequestDto.setUserId(Long.parseLong(memberDto.getLoginId()));
			Map<String,Object> result = donateService.donateInsert(donateRequestDto);	
			
			ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data"));			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);					
		}
		
		
		 /**
	     * 전체 기부 금액을 조회하여 반환합니다.
	     *
	     * @param donateRequestDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 총 기부 금액 및 상태 메시지를 담은 Map 객체
	     */
		@PostMapping("/donateTotal")
		public ResponseEntity donateTotal(@RequestBody DonateRequestDto donateRequestDto ,HttpServletRequest req,  HttpServletResponse res) {
						
			Map<String,Object> result = donateService.donateTotal(donateRequestDto);
			System.out.println(result);
			ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data"));			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);					
		}
		
		
		 /**
	     * 상위 기부자를 조회하여 반환합니다.
	     *
	     * @param donateRequestDto 기부 요청 정보를 담은 DTO
	     * @param req HttpServletRequest 요청 객체
	     * @param res HttpServletResponse 응답 객체
	     * @return 상위 기부자 성명, 금액 및 상태 메시지를 담은 Map 객체
	     */
		@PostMapping("/findTop5Donors")
		public ResponseEntity findTop5Donors(@RequestBody DonateRequestDto donateRequestDto ,HttpServletRequest req,  HttpServletResponse res) {
			Map<String,Object> result = donateService.findTop5Donors(donateRequestDto);
			ApiResponse response = new ApiResponse((boolean)result.get("state"), result.get("msg").toString() , result.get("data"));			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);					
		}
	    
	    
	}
