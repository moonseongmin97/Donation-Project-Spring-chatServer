package com.example.demo.auth.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.auth.dto.MemberDto;
import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import com.example.demo.auth.mapper.MemberMapper;
import com.example.demo.auth.repository.MemberRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class MemberService  {

	@Autowired  
    private  MemberRepository memberRepository;

	//@Autowired 
	//private  PasswordEncoder passwordEncoder;
	/*
	 * @Autowired(required=false) private MemberMapper memberMapper;
	 */

    
    @Autowired
    private  ModelMapper modelMapper;
    
    // 모든 회원 조회
    public List<MemberEntity> findAllMembers() {
    	System.out.println("두번쨰도 됨");
        return memberRepository.findAll();
    }

    // ID로 회원 조회
    public Optional<MemberEntity> findMemberById(MemberDto memberDto) {
    	//System.out.println("실행333===");
    	//String formattedUserId = String.format("%06d", ""); // 자바에서 6자리로 포맷팅
    	MemberEntity a = new MemberEntity();
    	a.setLoginId(memberDto.getId().toString());
    	a.setUserId(Long.parseLong("111111"));
    	///System.out.println("비즈니스로직==="+a.getUserId());
        return memberRepository.findById(a.getUserId());
    }
    
    


    // ID로 회원 조회
    public Map<String, Object> findActiveMemberByLoginId(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();        
       try {
	        MemberEntity requestDto = modelMapper.map(memberDto, MemberEntity.class);  //MemberRequestDto -> MemberEntity
	        
	        
	        //requestDto.setPasswordHash(passwordEncoder.encode(memberDto.getPasswordHash())); 
	        Optional<MemberEntity> response = memberRepository.findActiveMemberByLoginId(requestDto); // 가입할떄 쓰라는데?
	        
	        
	        if (response.isEmpty()) {  // 빈값 체크
	            result.put("state", false);
	            result.put("msg", "조회된 데이터가 없음");
	        } else {
	        	MemberResponseDto responseDto = modelMapper.map(response.get(), MemberResponseDto.class);  // MemberEntity -> MemberResponseDto
	        	//passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash()); //이게 회원 비밀번호 맞추는거 하는거
	        //	if(passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash())) {
	        		//비번 일치?
	       //	   System.out.println("비번 일치?");
	        //	}else {
	        		//비번 틀림?
	       // 		System.out.println("비번 틀림?");
	       // 	}
	            result.put("state", true);
	            result.put("msg", "회원 조회 성공");
	            result.put("data", responseDto);
	        }
       }catch (Exception e) {    	   
	           // 예외 처리
	           result.put("state", false);
	           result.put("msg", "회원 조회 중 오류 발생");
	           result.put("error", e.getMessage());
	}
       	return result;
    } 
    
    

    // 이메일로 회원 조회
    public MemberEntity findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 회원 저장
    public void saveMember(MemberEntity member) {
    	
    	String formattedUserId = String.format("%06d", ""); // 자바에서 6자리로 포맷팅
    	//MemberEntity  // 멤버 엔티티데 새로 세팅하자
    	
    	//member.setId()
    	member.setUserId(Long.parseLong("111111"));
        memberRepository.findById(Long.parseLong(member.getLoginId().toString()));
    }

}
