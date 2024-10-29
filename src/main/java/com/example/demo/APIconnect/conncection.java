package com.example.demo.APIconnect;


import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Cache.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.sql.DriverManager;
import java.time.Clock;


@RestController
public class conncection {
    @CrossOrigin(origins  = "/")
    @GetMapping("/check-db")
    public String checkDatabaseConnection() {
        boolean isConnected = PostgreSQLConnectionUtil.isConnected();
        return isConnected ? "DB 연결 성공" : "DB 연결 실패";

    }
}
