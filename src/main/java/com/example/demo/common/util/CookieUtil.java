package com.example.demo.common.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtil {

    // 특정 이름의 쿠키 값을 가져오는 메서드
    public static Optional<String> getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                         .filter(cookie -> name.equals(cookie.getName()))
                         .map(Cookie::getValue)
                         .findFirst();
        }
        return Optional.empty();
    }

    // 모든 쿠키 정보를 가져오는 메서드
    public static Cookie[] getAllCookies(HttpServletRequest request) {
        return request.getCookies() != null ? request.getCookies() : new Cookie[0];
    }
}
