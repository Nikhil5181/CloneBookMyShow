package com.jsp.CloneAPIBookMyShow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dto.AddressDTO;
import com.jsp.CloneAPIBookMyShow.entity.Address;
import com.jsp.CloneAPIBookMyShow.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Address>> saveAddress(@Validated @RequestBody AddressDTO address){
		return service.saveAddress(address);
	}
	

	@PutMapping
	public ResponseEntity<ResponseStructure<Address>> updateAddress(@Validated @RequestBody AddressDTO address , @RequestParam long addressId){
		return service.updateAddress(address,addressId);
	}
	
	
	@GetMapping
	public ResponseEntity<ResponseStructure<Address>> findAddressById(@RequestParam long addressId){
		return service.findAddressById(addressId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<Address>> deleteAddressById(@RequestParam long addressId){
		return service.deleteAddressById(addressId);
	}

}
