package com.skilldistillery.coderdojo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.coderdojo.entities.Address;
import com.skilldistillery.coderdojo.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository repo;

	@Override
	public List<Address> index() {
		return repo.findAll();
	}

	@Override
	public Address findById(int id) {
		Optional<Address> opt = repo.findById(id);
		Address addr = null;

		if (opt.isPresent()) {
			addr = opt.get();
		}
		return addr;
	}
	
	@Override
	public Address update(Address newAddr) {
		return repo.save(newAddr);
	}
}
