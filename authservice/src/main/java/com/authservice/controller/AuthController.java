package com.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.dto.APIResponse;
import com.authservice.dto.LoginDto;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;
import com.authservice.service.AuthService;
import com.authservice.service.JwtService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> register(@RequestBody UserDto userdto){
		APIResponse<String> response = authService.register(userdto);
		return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
	
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> loginCheck(@RequestBody LoginDto logindto){
		System.out.println("**********login 1 **********");
		APIResponse<String> response = new APIResponse<>();
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(logindto.getUsername(),logindto.getPassword());
		try {
		Authentication authenticate = authManager.authenticate(token);
		if(authenticate.isAuthenticated()) {
			String jwtToken = jwtService.generateToken(logindto.getUsername(),
	        authenticate.getAuthorities().iterator().next().getAuthority());
			System.out.println("**********login 2 **********");
			response.setMessage("Login Successful...");
			response.setStatus(200);
			response.setData(jwtToken);
			return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		response.setMessage("Login Failed....");
		response.setStatus(401);
		response.setData("UnAuthorized Access !!");
		return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
	}
	
	@GetMapping("/get-user")
	 public User getUser(@RequestParam String username) {
		 return userRepo.findByUsername(username);
	 }
}
