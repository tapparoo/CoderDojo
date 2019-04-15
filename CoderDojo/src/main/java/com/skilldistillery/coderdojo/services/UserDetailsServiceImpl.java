package com.skilldistillery.coderdojo.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.coderdojo.entities.Address;
import com.skilldistillery.coderdojo.entities.Role;
import com.skilldistillery.coderdojo.entities.User;
import com.skilldistillery.coderdojo.entities.UserDetail;
import com.skilldistillery.coderdojo.repositories.UserDetailRepository;
import com.skilldistillery.coderdojo.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailRepository deetsRepo;

	// Spring Security method(s)

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException(username);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : user.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}

	// Custom methods

	public List<UserDetail> index() {
		return deetsRepo.findAll();
	}

	// Find by UserDetail's embedded 'User' object's id
	public UserDetail findUserDetailByUsername(String username) {
		User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException(username);

		return deetsRepo.findByUserId(user.getId());
	}

	// Find by UserDetail id
	public UserDetail findById(long id) {
		Optional<UserDetail> opt = deetsRepo.findById(id);
		UserDetail deets = null;
		if (opt.isPresent()) {
			deets = opt.get();
		}
		return deets;
	}

	public UserDetail update(UserDetail ud) {
		UserDetail old = deetsRepo.findByUserId(ud.getId());
		if (old == null) {
			return null;
		}
		if (ud.getLocation() == null || ud.getLocation().getId() <= 0) {
			ud.setLocation(null);
		}

		if (ud.getAddress() == null || ud.getAddress().getId() <= 0) {
			ud.setAddress(new Address());
		}

		return deetsRepo.saveAndFlush(ud);
	}
}
