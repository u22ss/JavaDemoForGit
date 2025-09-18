package com.authservice.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authservice.jwt.JwtFilter;
import com.authservice.service.CustomerUserDetailsService;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {
	
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private JwtFilter filter;
	
	String[] publicEndpoints = {
	        "/api/v1/auth/register",
	        "/api/v1/auth/login",
	        "/api/v1/auth/update-password",
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	        "/swagger-ui.html",
	        "/swagger-resources/**",
	        "/webjars/**"
	    };

	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
    
	@Bean
	public AuthenticationProvider authProvider() {
		System.out.println("*****************1 ");

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customerUserDetailsService);
		authProvider.setPasswordEncoder(getEncoder());

		return authProvider;
	}
	
	 @Bean
		public SecurityFilterChain securityConfig(HttpSecurity http) throws Exception{
			
			http.authorizeHttpRequests( req -> {
				req.requestMatchers(publicEndpoints)
				   .permitAll()
				   .requestMatchers("/api/v1/admin/welcome").hasRole("ADMIN")
				   .anyRequest()
				   .authenticated();			
			}) .authenticationProvider(authProvider())
	        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
			
			
			    return http.csrf().disable().build();
		
	 }
}
