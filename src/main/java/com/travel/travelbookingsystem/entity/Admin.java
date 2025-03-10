package com.travel.travelbookingsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
}
