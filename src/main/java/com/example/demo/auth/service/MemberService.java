package com.example.demo.auth.service;

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
import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;
@Service
public class MemberService  {

	@Autowired  
    private  MemberRepository memberRepository;

	@Autowired 
	private  PasswordEncoder passwordEncoder;
    
    @Autowired
    private  ModelMapper modelMapper;
    
    @Autowired
    private RedisServiceImpl redisService;
    
    // 모든 회원 조회
    public List<MemberEntity> findAllMembers() {
    	System.out.println("두번쨰도 됨");
        return memberRepository.findAll();
    }

    // ID로 회원 조회
    public Optional<MemberEntity> findMemberById(MemberRequestDto memberDto) {
    	//System.out.println("실행333===");
    	//String formattedUserId = String.format("%06d", ""); // 자바에서 6자리로 포맷팅
    	MemberEntity a = new MemberEntity();
    	a.setLoginId(memberDto.getLoginId().toString());
    	a.setUserId(Long.parseLong("111111"));
    	///System.out.println("비즈니스로직==="+a.getUserId());
        return memberRepository.findById(a.getUserId());
    }
    
    
    // 회원 가입
    public Map<String, Object> joinMember(MemberRequestDto requestDto) {
        Map<String, Object> result = new HashMap<>();        
       try {    	   
	        MemberEntity memberEntity = modelMapper.map(requestDto, MemberEntity.class);  //MemberRequestDto -> MemberEntity	        
	        	        
	        if(memberRepository.existsByLoginId(memberEntity.getLoginId())) { //아이디 조회
	            result.put("state", false);
	            result.put("msg", "이미 아이디가 존재합니다.");
	            return result; 
	        }	        
	        Map<String, Object> validate= validateMemberEntity(memberEntity);  //유효성 검사
	        
	        if(!(boolean)validate.get("state")) {	        	
	        	return validate;                             
	        }	        
        	        	        
	        memberEntity.setPasswordHash(passwordEncoder.encode(requestDto.getPasswordHash())); // 패스워드 암호화
		      
		       memberRepository.joinMember(memberEntity);   // 회원 가입 	
		      
	            result.put("state", true);
	            result.put("msg", "회원 가입 성공.");	       
       	 	}catch (Exception e) {    	   
	           // 예외 처리
	           result.put("state", false);
	           result.put("msg", "회원 가입 중 오류 발생");
	           result.put("error", e.getMessage());
	           System.err.println("오류문 =="+e.getMessage());
	           e.printStackTrace();
       	 	}
       		return result;   	   	
    }
    
    // 필수 필드 유효성 검사
    private Map<String, Object> validateMemberEntity(MemberEntity memberEntity) {
    	Map<String, Object> result = new HashMap<>();   		
    		result.put("state", false);
    	
	        if (!StringUtils.hasText(memberEntity.getLoginId())) {        	
	        	result.put("msg", "loginId는 필수 입력 사항입니다.");
	        	 return result;
	        }
	        if (!StringUtils.hasText(memberEntity.getPasswordHash())) {       	
	        	result.put("msg", "비밀번호는 필수 입력 사항입니다.");
	        	 return result;
	        }
	        if (!StringUtils.hasText(memberEntity.getEmail())) {     
	        	result.put("msg", "이메일은 필수 입력 사항입니다.");	
	        	 return result;
	        }
	        // 비밀번호 정책 검사 (예: 길이, 특수 문자 포함 등)
	        if (memberEntity.getPasswordHash().length() < 8) {      	        	
	        	result.put("msg", "비밀번호는 최소 8자 이상이어야 합니다.");	
	        	 return result;
	        }        
	    	result.put("state", true);
	        // 추가 정책 (예: 대소문자, 숫자, 특수 문자 포함 확인)도 여기에 추가 가능
        return result;
    }
    
    


    // ID로 회원 조회
    public Map<String, Object> findActiveMemberByLoginId(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();        
       try {
	        MemberEntity requestDto = modelMapper.map(memberDto, MemberEntity.class);  //MemberRequestDto -> MemberEntity
	        	         
	        Optional<MemberEntity> response = memberRepository.findActiveMemberByLoginId(requestDto); // 가입할떄 쓰라는데?
	        
	        if (response.isEmpty()) {  // 빈값 체크
	            result.put("state", false);
	            result.put("msg", "아이디를 확인해주세요");
	        } else {
	        	MemberResponseDto responseDto = modelMapper.map(response.get(), MemberResponseDto.class);  // MemberEntity -> MemberResponseDto	        	//passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash()); //이게 회원 비밀번호 맞추는거 하는거			      			     
			      if(passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash())) { //패스워드 검증
			    	  
	    	            // JWT 생성
	    	            //String token = JwtProvider.generateToken();
	    				System.out.println("데이터 값 뽑아왔고 여기서 uuid 뽑아내자 아님 이걸 서비스 로직에서 해버려?===="+responseDto.getUuid());
	    	            // JWT를 HttpOnly 쿠키로 설정
	    	            Cookie jwtCookie = new Cookie("jwtToken", responseDto.getUuid());
	    	            jwtCookie.setHttpOnly(true);
	    	            jwtCookie.setSecure(false);  // 로컬 개발 환경에서는 false, 배포 환경에서는 true
	    	            jwtCookie.setPath("/");
	    	            jwtCookie.setMaxAge(60 * 60); // 1시간 유효
	    	            redisService.saveToken(responseDto.getUuid(), "test1");
	    	          result.put("jwt", jwtCookie);
				      result.put("state", true);
			          result.put("msg", "회원 조회 성공");
			          result.put("data", responseDto);			          			          
		    					          
			          
			          System.out.println("레디스 값 리턴 제발!!!!!!"+redisService.getValue("id"));
			          
			          
			      	return result;
			      }else {
				      result.put("state", false);
			          result.put("msg", "비밀 번호를 확인해주세요");
			      	return result;
			      }
	        }
       }catch (Exception e) {    		           // 예외 처리   
	           result.put("state", false);
	           result.put("msg", "회원 조회 중 오류 발생");
	           result.put("error", e.getMessage());
	           System.err.println("오류문 =="+e.getMessage());
	           e.printStackTrace();
	           
	}
       	return result;
    }
    
    
    // ID로 회원 조회
    public Map<String, Object> logoutMember(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();        
       try {
	        MemberEntity requestDto = modelMapper.map(memberDto, MemberEntity.class);  //MemberRequestDto -> MemberEntity
	        	         
	        //Optional<MemberEntity> response = memberRepository.findActiveMemberByLoginId(requestDto); // 가입할떄 쓰라는데?
	        String uuid = memberDto.getUuid();
	        if(uuid != null && redisService.getTokenKey(uuid)) {	        	
	        	redisService.deleteToken(memberDto.getUuid());
	        	//로그 아웃 로그 기록 남기기 
	        }
	        
	        
	        /*
	        if (response.isEmpty()) {  // 빈값 체크
	            result.put("state", false);
	            result.put("msg", "아이디를 확인해주세요");
	        } else {
	        	MemberResponseDto responseDto = modelMapper.map(response.get(), MemberResponseDto.class);  // MemberEntity -> MemberResponseDto	        	//passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash()); //이게 회원 비밀번호 맞추는거 하는거			      			     
			      if(passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash())) { //패스워드 검증
			    	  
	    	            // JWT 생성
	    	            //String token = JwtProvider.generateToken();
	    				System.out.println("데이터 값 뽑아왔고 여기서 uuid 뽑아내자 아님 이걸 서비스 로직에서 해버려?===="+responseDto.getUuid());
	    	            // JWT를 HttpOnly 쿠키로 설정
	    	            Cookie jwtCookie = new Cookie("jwtToken", responseDto.getUuid());
	    	            jwtCookie.setHttpOnly(true);
	    	            jwtCookie.setSecure(false);  // 로컬 개발 환경에서는 false, 배포 환경에서는 true
	    	            jwtCookie.setPath("/");
	    	            jwtCookie.setMaxAge(60 * 60); // 1시간 유효
	    	            redisService.saveToken(responseDto.getUuid(), "test1");
	    	          result.put("jwt", jwtCookie);
				      result.put("state", true);
			          result.put("msg", "회원 조회 성공");
			          result.put("data", responseDto);			          			          
		    					          
			          
			          System.out.println("레디스 값 리턴 제발!!!!!!"+redisService.getValue("id"));
			          
			          
			      	return result;
			      }else {
				      result.put("state", false);
			          result.put("msg", "비밀 번호를 확인해주세요");
			      	return result;
			      }
	        }
	        */
	        
	        
       }catch (Exception e) {    		           // 예외 처리   
	           result.put("state", false);
	           result.put("msg", "로그아웃 중 오류 발생");
	           result.put("error", e.getMessage());
	           System.err.println("오류문 =="+e.getMessage());
	           e.printStackTrace();
	           
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
