package com.example.demo.commonsvc.service;

import com.example.demo.commonsvc.Jpa.memberJpa;
import com.example.demo.commonsvc.controller.Login;
//import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class LoginService {

    @Autowired
    private memberJpa memberJpa;
    public String Login (Map<String, Object> param){

        memberJpa.findMember(param);
        System.out.println("되나?");

        return "HI";
    }

}
