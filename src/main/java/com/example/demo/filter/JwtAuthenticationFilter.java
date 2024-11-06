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

        System.out.println("쿠키 필터 체킹222=="+request.getServerPort()+"==="+request.getHeaderNames());
        if (token != null && jwtProvider.validateToken(token)) {
            String userId = redisService.getUserIdFromToken(token);  //레디스에서 값 체크

            if (userId != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } // 여기 레디스 값 없으면 401 리다이렉트 시켜서 비번 없다고 알려줘야할듯?
        }

        chain.doFilter(request, response);
    }
}
