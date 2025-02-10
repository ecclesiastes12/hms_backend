package com.hms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.jwt.JwtUtils;
import com.hms.modules.User;
import com.hms.security.request.LoginRequest;
import com.hms.security.response.LoginResponse;
import com.hms.security.response.UserAccountInfoResponse;
import com.hms.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private JwtUtils jwtUtils;

	// NB don't forget to create a bean for AuthenticationManager in the security
	// configuration
	private AuthenticationManager authenticationManager;

	private UserService userService;

	public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserService userService) {
		super();
		this.jwtUtils = jwtUtils;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	// NB signin endpoint always has to be public, no need for authentication or
	// protection like jwt for this endpoint
	// handler method for signin
	@PostMapping("/public/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {// takes LoginRequest object as a
																						// parameter
		Authentication authentication; // create authentication object
		try {
			// authenticate the user using UsernamePasswordAuthenticationToken object, and
			// this
			// object contains jwt with username and password, through the authentication
			// manager
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		} catch (AuthenticationException exception) {
			// catch exceptions that will occur during the authentication. thus error
			// response
			Map<String, Object> map = new HashMap<>(); // create a map object
			map.put("message", "Bad credentials");
			map.put("status", false);
			return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
		}

//	      set the authentication object in the security context holder to establish security context for the session.
		// this will mark the user as authenticated in the spring security context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// create UserDetails object of the authentication principal or object. this
		// contains the user credential, roles or authorities
		// assigned or granted to the user
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// generate token from username presented in UserDetails by invoking
		// generateTokenFromUsername in JwtUtils.java
		String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

		// Collect roles from the UserDetails and convert it to a list
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// Prepare the response body, now including the JWT token directly in the body
		// which contain the username,roles and jwttoken part
		LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);

		// Return the response entity with the JWT token included in the response body
		return ResponseEntity.ok(response);
	}

	// handler method that get the details of loggedIn user. Thus user account
	// details
	@GetMapping("/user")
	public ResponseEntity<?> userAccountInfo(@AuthenticationPrincipal UserDetails userDetails) {

		// extract the username from the authentication principal
		User user = userService.findByUsername(userDetails.getUsername());

		// get the role from authenticated user and convert it to list of roles
		List<String> roles = userDetails.getAuthorities().stream().map(role -> role.getAuthority())
				.collect(Collectors.toList());

		// create a UserAccountInfoResponse object that contains user account
		// information
		UserAccountInfoResponse userAccountInfo = new UserAccountInfoResponse(user.getId(), user.getFirstName(),
				user.getLastName(), user.getMaidenName(), user.getUserName(), user.getEmail(), roles);
		
		return ResponseEntity.ok(userAccountInfo);
	}

	// handler method that get the username of the current loggedIn user	
	@GetMapping("/username")
	public String currentLoggedInUsername(@AuthenticationPrincipal UserDetails userDetails) {
		return userDetails != null ? userDetails.getUsername() : "";
	}
	

}
