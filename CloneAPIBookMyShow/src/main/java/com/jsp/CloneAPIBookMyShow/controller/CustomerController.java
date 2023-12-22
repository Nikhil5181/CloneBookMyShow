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
import com.jsp.CloneAPIBookMyShow.dto.CustomerDTO;
import com.jsp.CloneAPIBookMyShow.entity.Customer;
import com.jsp.CloneAPIBookMyShow.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<CustomerDTO>> saveCustomer(@Validated @RequestBody Customer customer){
		return service.saveCustomer(customer);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<CustomerDTO>> updateCustomer(@Validated @RequestBody Customer customer ,@RequestParam long customerId){
		return service.updateCustomer(customer,customerId);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<CustomerDTO>> findCustomerById(@RequestParam long customerId){
		return service.findCustomerById(customerId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<CustomerDTO>> deleteCustomerById(@RequestParam long customerId){
		return service.deleteCustomerById(customerId);
	}
	
	@GetMapping("/login")
	public ResponseEntity<ResponseStructure<CustomerDTO>> loginCustomer(String email,String Password){
		return service.loginCustomer(email, Password);
	}
}