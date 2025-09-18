package com.authservice.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.dto.APIResponse;
import com.authservice.dto.UserDto;
import com.authservice.entity.User;
import com.authservice.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public APIResponse<String> register(UserDto userdto){
		
		if(userRepo.existsByEmail(userdto.getEmail())) {
			APIResponse<String> response = new APIResponse<>();
			response.setStatus(409);
			response.setMessage("Registration Failed..........");
			response.setData("User With Email Already Exist !!!");
			return response;
		}
		
		if(userRepo.existsByUsername(userdto.getUsername())) {
			APIResponse<String> response = new APIResponse<>();
			response.setMessage("Registration Failed.....");
			response.setStatus(409);
			response.setData("User With Username Already Exist !!!");
			return response;
		}
		
		User user = new User();
		BeanUtils.copyProperties(userdto, user);
		user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		
		userRepo.save(user);
		
		APIResponse<String> response = new APIResponse<>();
		response.setMessage("Registration Successfully......");
		response.setData("User Registered !!!");
		response.setStatus(200);
		
		return response;
	}
}
