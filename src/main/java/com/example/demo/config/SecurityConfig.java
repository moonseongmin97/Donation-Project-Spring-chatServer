package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.common.redis.service.RedisServiceImpl;
import com.example.demo.common.util.JwtProvider;
import com.example.demo.filter.JwtAuthenticationFilter;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	RedisServiceImpl redisService;
	
	@Autowired
	JwtProvider jwtProvider;
	
	   @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        System.out.println("Security filter chain with no authentication");

	        http
	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 적용
	            .csrf().disable()  // CSRF 비활성화
	            .authorizeRequests(auth -> auth
	                .anyRequest().permitAll()  // 모든 요청을 인증 없이 허용
	    	         )
	                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // JWT 필터 추가


	        return http.build();
	    }
	   


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtProvider, redisService);
    }
    
    
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	System.out.println("cors 허용 쿠키 허용");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);  // 쿠키 전송 허용
        configuration.addAllowedOrigin("http://localhost:3000");  // React 앱 주소
        configuration.addAllowedMethod("*");  // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*");  // 모든 헤더 허용
        configuration.addExposedHeader("Authorization");  // JWT 토큰 노출 허용 (필요 시)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
