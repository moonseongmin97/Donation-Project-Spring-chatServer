package com.example.demo.donate.payment.controller;	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
	@RequestMapping("/auth")
	public class PaymentController {
		
	    @Value("${kftc.client.id}")
	    private String kftcId; 
	    
	    @Value("${kftc.client.secret}")
	    private String kftcSecret;
		
	    @Value("${kftc.client.redirectUrl}")
	    private String kftcRedirectUrl;
	    
	    @Value("${kftc.server.url}")
	    private String kftcServerUrl;
	    
		
		    @PostMapping("/getToken")
		    public ResponseEntity<String> getToken(@RequestBody Map<String, String> request) {
		        String clientId = kftcId;
		        String clientSecret = kftcSecret;
		        String redirectUri = kftcRedirectUrl;
		        String authCode = request.get("code");

		        RestTemplate restTemplate = new RestTemplate();
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		        body.add("client_id", clientId);
		        body.add("client_secret", clientSecret);
		        body.add("redirect_uri", redirectUri);
		        body.add("code", authCode);
		        body.add("grant_type", "authorization_code");

		        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
		        ResponseEntity<String> response = restTemplate.exchange(kftcServerUrl, HttpMethod.POST, requestEntity, String.class);

		        return response;
		    }

		        @GetMapping("/callback")
		        public ResponseEntity<String> handleCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
		            System.out.println("Authorization Code: " + code);
		            return ResponseEntity.ok("Authorization Code: " + code);
		        }
		    

    
	}
