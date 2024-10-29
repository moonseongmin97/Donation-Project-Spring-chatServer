package com.example.demo.APIconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnectionUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/your_database";  // 데이터베이스 URL
    private static final String USERNAME = "your_username";  // 데이터베이스 사용자 이름
    private static final String PASSWORD = "your_password";  // 데이터베이스 비밀번호

    /**
     * PostgreSQL 데이터베이스 연결 상태를 확인하는 메서드
     *
     * @return 연결 성공 시 true, 실패 시 false
     */
    public static boolean isConnected() {
        Connection conn = null;

        try {
            // PostgreSQL 드라이버 로드
            Class.forName("org.postgresql.Driver");

            // 데이터베이스 연결 시도
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DONATE_DB1", "postgres", "1111");
            System.out.println("PostgreSQL 데이터베이스에 성공적으로 연결되었습니다!");
            return true;

        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
            return false;

        } catch (SQLException e) {
            System.err.println("PostgreSQL 데이터베이스 연결 실패!");
            e.printStackTrace();
            return false;

        } finally {
            // 연결 닫기
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("연결이 종료되었습니다.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}