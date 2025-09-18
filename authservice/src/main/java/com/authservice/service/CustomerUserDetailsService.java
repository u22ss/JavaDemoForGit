package com.authservice.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authservice.entity.User;
import com.authservice.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("************************* till here all ok!! " );

		User user = userRepo.findByUsername(username);
		System.out.println("Role from DB: ************************" + user.getRole());
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority(user.getRole())));	
		}

}
