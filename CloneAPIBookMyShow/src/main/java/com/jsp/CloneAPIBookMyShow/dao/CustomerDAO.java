package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Customer;
import com.jsp.CloneAPIBookMyShow.exception.EmailNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.PhoneNumberNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.CustomerRepo;

@Repository
public class CustomerDAO {

	@Autowired
	CustomerRepo customerRepo;
	
	public Customer saveCustomer(Customer customer) {
		return customerRepo.save(customer);
	}
	
	public Customer findCustomerById(long customerId) {
		Optional<Customer> op = customerRepo.findById(customerId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("customer not found you provided Id.. !");
	}
	
	public Customer findCustomerByPhoneNumber(long phoneNumber) {
		Optional<Customer> op = customerRepo.findCustomerByCustomerPhone(phoneNumber);
		if(op.isPresent())  
			return op.get();
			
			throw new PhoneNumberNotFoundException("Given Phone Number Cutomer Not Found....! ");	
	}
		

	public Customer findCustomerByEmail(String email) {
		Optional<Customer> op = customerRepo.findCustomerByCustomerEmail(email);			
		if(op.isPresent())  
			return op.get();
					
		throw new EmailNotFoundException("Given Email Cutomer Not Found....! ");	
	}	
	
		
	public void deleteCustomerById(long customerId) {
			findCustomerById(customerId);
	//if above method not throw exception means this id present in database then delete this object in database
			customerRepo.deleteById(customerId);
	}
	
	
	
}
