package com.example.demo.donate.payment.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.auth.dto.MemberDto;
import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.auth.mapper.MemberMapper;
import com.example.demo.auth.repository.MemberRepository;
import com.example.demo.common.dto.ResponseDto;
import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.util.StringUtils;
import com.example.demo.donate.bank.entity.BankEntity;
import com.example.demo.donate.donate.entity.DonateEntity;
import com.example.demo.donate.donate.repository.DonateRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
@Service
public class PaymentService  {


	
    @Autowired
    private RedisServiceImpl redisService;
    
    // 모든 회원 조회
    public List<DonateEntity> findBankList() {
    	
    	
        return null;
    }

}
