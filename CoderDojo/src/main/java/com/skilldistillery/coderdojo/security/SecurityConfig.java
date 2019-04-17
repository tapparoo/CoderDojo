package com.skilldistillery.coderdojo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// XXX: This is where form login is enabled to go through Spring Security
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/api/**", "/**").permitAll()
		.antMatchers("/resources/**", "/register", "/authenticate").permitAll()
		.antMatchers("/").permitAll()
		.antMatchers("/api/meetings/locations/**").permitAll()
		.antMatchers("/api/meetings").hasAuthority("ADMIN")
		.antMatchers("/api/locations").permitAll()
		.antMatchers("/api/schedule").permitAll()
		.antMatchers("/api/users/**").authenticated()
		.antMatchers("/api/users/**/**").authenticated()
		.antMatchers("//all-achievements").hasAnyAuthority("ADMIN")
		.antMatchers("//api/usergoals/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/user_achievement/**'").hasAnyAuthority("ADMIN")
		.antMatchers("/api/users/roles/**").hasAuthority("ADMIN")
		.antMatchers("/api/users/**/roles").hasAuthority("ADMIN")
		.antMatchers("/api/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
        .and().httpBasic();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}


	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}