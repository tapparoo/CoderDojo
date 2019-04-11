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
		.antMatchers("/resources/**", "/registration", "/login", "/logout").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers("/api/**").hasAuthority("ADMIN")
		.antMatchers("/api/users/").hasAuthority("STUDENT")
		.anyRequest().authenticated()
		.and()
	    .formLogin()
        .loginPage("/login")
        .permitAll();
//			.authorizeRequests().antMatchers("/resources/**", "/registration").permitAll().anyRequest().authenticated()
//			.and()
//			.formLogin().loginPage("/login").permitAll()
//			.and()
//			.logout().permitAll();
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