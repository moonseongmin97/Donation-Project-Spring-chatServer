package com.example.demo.commonsvc.controller;


import com.example.demo.DemoApplication;
import com.example.demo.commonsvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



@RestController
public class Login {


    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins  = "/")
    @GetMapping("/api/hello")
    //@RequestMapping("/api")
    public String Hello(){

    	//String a=DemoApplication.findMember2();
    	
        return "hi";
    }
    @CrossOrigin(origins  = "/")
    //@PostMapping("/api/login")
    @RequestMapping(value = "/api/login", method = {RequestMethod.GET, RequestMethod.POST})
    //@RequestMapping("/api")
    public String Login(HttpServletRequest request, @RequestBody Map<String, Object> paramMap){

    	
    	String a = request.getParameter("param").toString();	
        System.out.println("찍힘="+a);


        return "";
    }

    @CrossOrigin(origins  = "/")
    @PostMapping("/api/Join")
    //@RequestMapping("/api")
    public String Join(HttpServletRequest request, @RequestBody Map<String, Object> paramMap){
        System.out.println("결과 값1111===="+paramMap);


        String b =  loginService.Login(paramMap);

        System.out.println("결과 값222===="+b);


        return "";
    }

}
