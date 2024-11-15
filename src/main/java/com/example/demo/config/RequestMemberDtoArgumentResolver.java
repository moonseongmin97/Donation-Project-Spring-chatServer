package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.util.CookieUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Component
public class RequestMemberDtoArgumentResolver implements HandlerMethodArgumentResolver {


	@Autowired private RedisServiceImpl redisServiceImpl;
	
    @PostConstruct
    public void init() {
        System.out.println("RedisServiceImpl 주입 상태: " + (redisServiceImpl != null));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();                      
        MemberRequestDto memberRequestDto = new MemberRequestDto();        
        boolean jwtCheck = CookieUtil.getCookieValue(request, "jwtToken").isEmpty(); // 쿠키 안에 토큰 값 확인
        
        if (!jwtCheck ) { 
        	String token  = CookieUtil.getCookieValue(request, "jwtToken").get();
        	if(redisServiceImpl.getTokenKey(token)) {
             	ObjectMapper objectMapper = new ObjectMapper();
             	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);   //필드 값 없어도 무시          	
             	try {
        		String userInfo =redisServiceImpl.getUserIdFromToken(token);
        		 memberRequestDto = objectMapper.readValue(userInfo, MemberRequestDto.class);
        		
             	}catch(Exception e){
             		e.getStackTrace();
             	}
                //이거 rsa 암호화해서 토큰키 변경시키고
                //클라잉언트에 토큰 주는것도 암호화 한거 주자..                    
                                 
        	}        	               
        }

        return memberRequestDto;
    }
    
    
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // RequestMemberDto 타입의 매개변수가 있을 때만 동작하도록 설정
        return parameter.getParameterType().equals(MemberRequestDto.class);
    }
    
    
    
    
}