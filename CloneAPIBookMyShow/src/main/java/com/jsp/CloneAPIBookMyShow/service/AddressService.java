package com.jsp.CloneAPIBookMyShow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.AddressDAO;
import com.jsp.CloneAPIBookMyShow.dto.AddressDTO;
import com.jsp.CloneAPIBookMyShow.entity.Address;

@Service
public class AddressService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AddressDAO addressDao;

	
	public ResponseEntity<ResponseStructure<Address>> saveAddress(AddressDTO address) {	
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
									"Address successfully added..",
									addressDao.saveAddress(this.modelMapper.map(address,Address.class))),
									HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Address>> updateAddress(AddressDTO address, long addressId) {
		addressDao.findAddressById(addressId);
		Address newAddress = this.modelMapper.map(address,Address.class);
		newAddress.setAddressId(addressId);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(),
									"Address updated...",
									addressDao.saveAddress(newAddress)),
									HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Address>> findAddressById(long addressId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Address Found...",
									addressDao.findAddressById(addressId)),
									HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<Address>> deleteAddressById(long addressId) {
		addressDao.deleteAddrssById(addressId);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
										"Address Deleted...",
										null),
										HttpStatus.FOUND);
	}

}
