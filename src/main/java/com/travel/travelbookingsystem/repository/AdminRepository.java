package com.travel.travelbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.travelbookingsystem.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	Admin findByEmail(String email);

}