//package com.hms.modules;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import jakarta.persistence.Column;
//
//public class UserDTO {
//
//    @JsonProperty("first_name")
//    private String firstName;
//
//    @JsonProperty("last_name")
//    private String lastName;
//    
//    @JsonProperty("maiden_name")
//    private String maidenName;
//    
//    private String username;
//
//    private String email;
//
//    private String password;
//    
//    @JsonProperty("date_of_birth")
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//	private LocalDate dateOfBirth;
//	
//	
//	@JsonProperty("phone_number")
//	private String phoneNumber;
//
//    private Set<String> roles; // Accepts role names as strings
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public Set<String> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<String> roles) {
//		this.roles = roles;
//	}
//
//	public String getMaidenName() {
//		return maidenName;
//	}
//
//	public void setMaidenName(String maidenName) {
//		this.maidenName = maidenName;
//	}
//
//	public LocalDate getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public void setDateOfBirth(LocalDate dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//   
//    
//}
