package com.skilldistillery.coderdojo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
	// XXX: Use the BCryptPasswordEncoder here. Created an MD5 hash encoder because
	// it allowed me to hardcode a user in src/main/resources/import.sql
	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new PasswordEncoder() {
//			@Override
//			public String encode(CharSequence charSequence) {
//				return getMd5(charSequence.toString());
//			}
//
//			@Override
//			public boolean matches(CharSequence charSequence, String s) {
//				return getMd5(charSequence.toString()).equals(s);
//			}
//		};
//	}
//
//	public static String getMd5(String input) {
//		try {
//			// Static getInstance method is called with hashing SHA
//			MessageDigest md = MessageDigest.getInstance("MD5");
//
//			// digest() method called
//			// to calculate message digest of an input
//			// and return array of byte
//			byte[] messageDigest = md.digest(input.getBytes());
//
//			// Convert byte array into signum representation
//			BigInteger no = new BigInteger(1, messageDigest);
//
//			// Convert message digest into hex value
//			String hashtext = no.toString(16);
//
//			while (hashtext.length() < 32) {
//				hashtext = "0" + hashtext;
//			}
//
//			// System.out.println("******************" + hashtext);
//
//			return hashtext;
//		}
//
//		// For specifying wrong message digest algorithms
//		catch (NoSuchAlgorithmException e) {
//			System.out.println("Exception thrown" + " for incorrect algorithm: " + e);
//			return null;
//		}
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// XXX: This is where form login is enabled to go through Spring Security
		http.authorizeRequests().antMatchers("/resources/**", "/registration").permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll().and().logout().permitAll();
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