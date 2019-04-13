package com.skilldistillery.coderdojo.services;

import java.util.List;

import com.skilldistillery.coderdojo.entities.Address;

public interface AddressService {
	List<Address> index();
	Address findById(int id);
	Address update(Address newAddr);
}
