package com.example.demo.commonsvc.to;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import javax.persistence.*;


@Entity
@Table (name = "users")
@Getter
@Setter
public class MemberEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
}
