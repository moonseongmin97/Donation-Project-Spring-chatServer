package com.example.demo.APIconnect;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;


@RestController
public class conncection {
    //@CrossOrigin(origins  = "/")
    //@GetMapping("/api/hello")
    //@RequestMapping("/api")
    public String Hello(){
        return "hi hi";
    }
}
