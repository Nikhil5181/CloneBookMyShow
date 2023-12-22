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
import com.jsp.CloneAPIBookMyShow.dto.OwnerDTO;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.service.OwnerService;

@RestController
@RequestMapping("/owner")
public class OwnerController {
	
	@Autowired
	private OwnerService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<OwnerDTO>> saveOwner(@Validated @RequestBody Owner owner){
		return service.saveOwner(owner);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<OwnerDTO>> updateOwner(@Validated @RequestBody Owner owner){
		return service.updateOwner(owner);
	}
	
	@GetMapping("/id")
	public ResponseEntity<ResponseStructure<OwnerDTO>> findOwnerById(@RequestParam long ownerId){
		return service.findOwnerById(ownerId);
	}
	
	@GetMapping("/email")
	public ResponseEntity<ResponseStructure<OwnerDTO>> findOwnerByEmail(@RequestParam String email){
		return service.findByEmail(email);
	}
	
	@GetMapping("/phonenumber")
	public ResponseEntity<ResponseStructure<OwnerDTO>> findOwnerByPhoneNumber(@RequestParam long phoneNumber){
		return service.findByPhoneNumber(phoneNumber);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<OwnerDTO>> removeOwnerById(@RequestParam long ownerId){
		return service.deleteOwnerById(ownerId);
	}
	
	@GetMapping("/login")
	public ResponseEntity<ResponseStructure<OwnerDTO>> loginOwner(@RequestParam String ownerEmail,@RequestParam String ownerPassword){
		return service.loginOwner(ownerEmail,ownerPassword);
	}
	
}