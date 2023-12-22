package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Address;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.AddressRepo;

@Repository
public class AddressDAO {

	@Autowired
	AddressRepo addressRepo;
	
	public Address saveAddress(Address address) {
		return addressRepo.save(address);
	}
	
	public Address findAddressById(long addressId) {
		Optional<Address> op = addressRepo.findById(addressId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("You provided address id does not existed please send correct ID..!");
	}
	
	public void deleteAddrssById(long addressId) {
		findAddressById(addressId);
		addressRepo.deleteById(addressId);
	}	
}