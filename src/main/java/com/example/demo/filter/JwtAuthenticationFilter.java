package com.example.demo.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.common.redis.service.RedisService;
import com.example.demo.common.util.JwtProvider;

import java.io.IOException;
import java.util.ArrayList;



public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
    private  JwtProvider jwtProvider;
	
	@Autowired
    private  RedisService redisService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, RedisService redisService) {
        this.jwtProvider = jwtProvider;
        this.redisService = redisService;
        System.out.println("jwt 필터 실행");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	System.err.println("jwt필터");
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
            System.out.println("쿠키 필터 체킹");
        }

        	System.out.println("필터 -if문 통과 전 =="+token);
        	//token="g2";
        	
        if (token != null ) {  // 토큰 값 없음... 비회원       	     	
	            if (redisService.getTokenKey(token)) { //레디스에 토큰키 확인 
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
	                SecurityContextHolder.getContext().setAuthentication(authentication); // 이게 뭐하는 걸까....
	                
	                System.out.println("토큰 값 있고 레디스도 있고 통과");
	            }else {                                   //토큰 값 존재 회원 but 레디스 없음 만료?
	            	System.out.println("토큰 값 있지만 레디스에 없음");
	                // 토큰 만료로 인해 401 응답 설정
    	            Cookie jwtCookie = new Cookie("jwtToken", token);
		    	    jwtCookie.setHttpOnly(true);
		    	    jwtCookie.setMaxAge(0); // 만료 시간 0으로 설정
		    	    jwtCookie.setPath("/"); // 쿠키 경로 설정		
		    	    
		    	    response.addCookie(jwtCookie);
		    	    
	                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                response.getWriter().write("토큰이 만료되었습니다. 다시 로그인 해주세요.");
	                response.getWriter().flush();
	                return;           	
	            	//토큰값 만료로 로그인 다시 요청
	                // 여기 레디스 값 없으면 401 리다이렉트 시켜서 비번 없다고 알려줌 할듯?           
	            }  
        }

        chain.doFilter(request, response);
    }
}
