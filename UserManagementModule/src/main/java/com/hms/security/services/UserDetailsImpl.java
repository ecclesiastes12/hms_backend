package com.hms.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hms.modules.User;

//This class acts as a bridge between the application's user representation (the User entity) 
//and Spring Security's UserDetails interface. thus it create a connection between the custom
//user module also know as User entity(User.java) and UserDetails interface

public class UserDetailsImpl implements UserDetails{
	
	
	private static final long serialVersionUID = 1L;
	
	//fields that will be need in the implementation of UserDetails.class
	//Thus the user information needed for the implementation of UserDetails.class
	//The field or variables used here store all the user information needed and must match the field or variable that will be used 
	//authentication
	
	//field from User.java
	private Long id; //Represents the unique identifier for the user. 
	
	//Stores the username of the user.
	private String username;
	
	//Stores the user's email address.
	private String email;
	
	// The password field is annotated with @JsonIgnore to prevent it from being serialized
	//and exposed in JSON responses.
	@JsonIgnore
	private String password;
	
	//creates authority object. NB GrantedAuthority Represents an authority granted to an authenticated object(user).
	//it is typically used to define user roles
	//private Collection<? extends GrantedAuthority> authorities;
	private Collection<? extends GrantedAuthority> authorities;
	
	//constructor
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	
	// SimpleGrantedAuthority is basic concrete implementation of a GrantedAuthority. It stores a String representation of an authority granted to the
		// Authentication object(user). It converts User entity object(User user) into UserDetails Implementation object(UserDetailsImpl)
	public static UserDetailsImpl build(User user) {
//		Collection<GrantedAuthority> authority = user.getRoles().stream().map(
//				role -> new SimpleGrantedAuthority(role.getRoleName().name()))
//				.collect(Collectors.toList());
		Collection<? extends GrantedAuthority> authority = (Collection<? extends GrantedAuthority>) user.getRoles().stream().map(
				role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toSet());
		
		return new UserDetailsImpl(
				user.getId(), 
				user.getUserName(),
				user.getEmail(), 
				user.getPassword(), 
				authority
				);
	}	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stu
		return username;
	}

	//method that return email of authenticated user
	public String getEmail() {
		return email;
	}

}
