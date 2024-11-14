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
import com.example.demo.common.dto.ResponseDto;
import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.Cookie;

@Service
public class MemberService {

    @Autowired  
    private MemberRepository memberRepository;

    @Autowired 
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private RedisServiceImpl redisService;

    /**
     * 모든 회원 조회
     * @return List<MemberEntity> 모든 회원의 목록
     */
    public List<MemberEntity> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * ID로 회원 조회
     * @param memberDto 조회할 회원의 정보를 담은 DTO
     * @return Optional<MemberEntity> 회원 엔티티 객체를 Optional로 감싸서 반환
     */
    public Optional<MemberEntity> findMemberById(MemberRequestDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setLoginId(memberDto.getLoginId().toString());
        memberEntity.setUserId(Long.parseLong("111111"));
        return memberRepository.findById(memberEntity.getUserId());
    }

    /**
     * 회원 가입
     * @param requestDto 회원 가입 정보를 담은 DTO
     * @return Map<String, Object> 상태 메시지를 담은 결과
     */
    public Map<String, Object> joinMember(MemberRequestDto requestDto) {
        Map<String, Object> result = new HashMap<>();
        try {
            MemberEntity memberEntity = modelMapper.map(requestDto, MemberEntity.class);

            // 아이디 중복 확인
            if (memberRepository.existsByLoginId(memberEntity.getLoginId())) {
                result.put("state", false);
                result.put("msg", "이미 아이디가 존재합니다.");
                return result;
            }

            // 필수 필드 유효성 검사
            Map<String, Object> validate = validateMemberEntity(memberEntity);
            if (!(boolean) validate.get("state")) {
                return validate;
            }

            // 비밀번호 암호화 및 회원 등록
            memberEntity.setPasswordHash(passwordEncoder.encode(requestDto.getPasswordHash()));
            memberRepository.joinMember(memberEntity);

            result.put("state", true);
            result.put("msg", "회원 가입 성공.");
        } catch (Exception e) {
            result.put("state", false);
            result.put("msg", "회원 가입 중 오류 발생");
            result.put("error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 필수 필드 유효성 검사
     * @param memberEntity 검증할 회원 엔티티
     * @return Map<String, Object> 유효성 검사 결과
     */
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
        if (memberEntity.getPasswordHash().length() < 8) {
            result.put("msg", "비밀번호는 최소 8자 이상이어야 합니다.");
            return result;
        }

        result.put("state", true);
        return result;
    }

    /**
     * 로그인 - (아이디와 비밀번호 검증 포함)
     * @param memberDto 회원의 로그인 정보를 담은 DTO
     * @return Map<String, Object> 로그인 상태 및 메시지를 담은 결과
     */
    public Map<String, Object> findActiveMemberByLoginId(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        try {
            MemberEntity requestDto = modelMapper.map(memberDto, MemberEntity.class);
            Optional<MemberEntity> response = memberRepository.findActiveMemberByLoginId(requestDto);

            if (response.isEmpty()) {
                result.put("state", false);
                result.put("msg", "아이디를 확인해주세요");
            } else {
                MemberResponseDto responseDto = modelMapper.map(response.get(), MemberResponseDto.class);
                if (passwordEncoder.matches(memberDto.getPasswordHash(), response.get().getPasswordHash())) {
                	ObjectMapper objectMapper = new ObjectMapper();
                    Cookie jwtCookie = new Cookie("jwtToken", responseDto.getUuid());
                    jwtCookie.setHttpOnly(true);
                    jwtCookie.setSecure(false);
                    jwtCookie.setPath("/");
                    jwtCookie.setMaxAge(60 * 60);
                    
                    //이거 rsa 암호화해서 토큰키 변경시키고
                    //클라잉언트에 토큰 주는것도 암호화 한거 주자..                    
                    String jsonValue = objectMapper.writeValueAsString(responseDto); //직렬화                    
                    redisService.saveToken(responseDto.getUuid(), jsonValue);
                    String userInfo=redisService.getUserIdFromToken(responseDto.getUuid());  // -> 여기가 반환값이 스트링                                    	                    
                    //Map  userInfoMap = objectMapper.readValue(userInfo, Map.class);    //역직렬화                
                    //System.out.println("userInfoMap==="+userInfoMap.get("loginId")); 
                    
                    result.put("jwt", jwtCookie);
                    result.put("state", true);
                    result.put("msg", "회원 조회 성공");
                    result.put("data", responseDto);
                } else {
                    result.put("state", false);
                    result.put("msg", "비밀 번호를 확인해주세요");
                }
            }
        } catch (Exception e) {
            result.put("state", false);
            result.put("msg", "회원 조회 중 오류 발생");
            result.put("error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 로그아웃 처리
     * @param memberDto 로그아웃할 회원 정보가 담긴 DTO
     * @return Map<String, Object> 로그아웃 처리 결과
     */
    public Map<String, Object> logoutMember(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        try {
            String uuid = memberDto.getUuid();
            if (uuid != null && redisService.getTokenKey(uuid)) {
                redisService.deleteToken(memberDto.getUuid());
                result.put("state", true);
                result.put("msg", "로그아웃 성공");
            }
        } catch (Exception e) {
            result.put("state", false);
            result.put("msg", "로그아웃 중 오류 발생");
            result.put("error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 쿠키를 이용한 로그인 세션 토큰 검증
     * @param memberDto 세션을 확인할 회원 정보가 담긴 DTO
     * @return Map<String, Object> 세션 상태 및 메시지를 담은 결과
     */
    public Map<String, Object> findSessionMember(MemberRequestDto memberDto) {
        Map<String, Object> result = new HashMap<>();
        MemberResponseDto responseDto = new MemberResponseDto();
        try {
            String uuid = memberDto.getUuid();
            
            if( uuid==null && !memberDto.getLoginYn() ){ // jwt 토큰 x , 로그인 상태 x  비회원 로그인
                result.put("state", true);
                result.put("msg", "비회원 로그인");
                result.put("data", responseDto);
            }else if (uuid == null && memberDto.getLoginYn()) {  // 클라이언트 토큰 없고  로그인 상태 o  -> 로그아웃 시켜야지            	
                result.put("state", false);
                result.put("msg", "사용자 토큰이 존재하지 않습니다. 재로그인 부탁드립니다.");
                result.put("data", responseDto);            	
            }else if (uuid != null && redisService.getTokenKey(uuid)) {
                String sessionResult = redisService.getUserIdFromToken(uuid);
                responseDto.setLoginYn(true);
                result.put("state", true);
                result.put("msg", "로그인 토큰 정상");
                result.put("data", responseDto);
            }else if (uuid != null && !redisService.getTokenKey(uuid)) {             	
                Cookie jwtCookie = new Cookie("jwtToken", uuid);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setMaxAge(0);
                jwtCookie.setPath("/");
                result.put("jwt", jwtCookie);
                result.put("state", false);
                result.put("msg", "서버 토큰 만료 재로그인 부탁드리겠습니다.");           	     	
            } else {
                Cookie jwtCookie = new Cookie("jwtToken", uuid);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setMaxAge(0);
                jwtCookie.setPath("/");

                result.put("jwt", jwtCookie);
                result.put("state", false);
                result.put("msg", "로그인 토큰 확인");
            }
        } catch (Exception e) {
            result.put("state", false);
            result.put("msg", "로그아웃 중 오류 발생");
            result.put("error", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 이메일로 회원 조회
     * @param email 조회할 회원의 이메일
     * @return MemberEntity 회원 엔티티
     */
    public MemberEntity findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

}
