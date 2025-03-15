package com.travel.travelbookingsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity // Marks this class as a JPA entity, meaning it is mapped to a database table
@Table(name = "passenger") // Specifies the table name in the database. tble name will be "passenger"
public class Passenger {

    @Id // Marks the primary key of the entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Specifies auto-increment behavior for the primary key (passengerId).
    private long passengerId;

    @JsonProperty("fullName")  // Ensures JSON field "fullName" maps to "full_name"
    @Column(name = "full_name", nullable = false)
    private String full_name;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "gender", nullable = false)
    private String gender;
    
    @Column(name = "age", nullable = false)
    private int age;
    
    @Column(name = "contact", nullable = false)
    private String contact;

	public long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", full_name=" + full_name + ", email=" + email + ", password="
				+ password + ", gender=" + gender + ", age=" + age + ", contact=" + contact + "]";
	}
    
	
    
}
