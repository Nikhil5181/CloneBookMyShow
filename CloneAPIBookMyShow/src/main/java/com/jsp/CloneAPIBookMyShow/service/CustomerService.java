package com.jsp.CloneAPIBookMyShow.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.CustomerDAO;
import com.jsp.CloneAPIBookMyShow.dto.CustomerDTO;
import com.jsp.CloneAPIBookMyShow.entity.Customer;
import com.jsp.CloneAPIBookMyShow.exception.InvalidUsernameAndPasswordException;

@Service
public class CustomerService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerDAO customerDao;
	
	
	public ResponseEntity<ResponseStructure<CustomerDTO>> saveCustomer(Customer customer) {

		CustomerDTO custDTO = this.modelMapper.map(customerDao.saveCustomer(customer),CustomerDTO.class);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
								"Customer added..", 
								custDTO),
								HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<CustomerDTO>> updateCustomer(Customer customer, long customerId) {
		customer.setCustomerId(customerDao.findCustomerById(customerId).getCustomerId());
		customerDao.saveCustomer(customer);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(),
									"Customer updated successfully...",
									this.modelMapper.map(customer,CustomerDTO.class)),
									HttpStatus.OK); 
	}

	
	public ResponseEntity<ResponseStructure<CustomerDTO>> findCustomerById(long customerId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Customer found...",
									this.modelMapper.map(customerDao.findCustomerById(customerId),CustomerDTO.class)),
									HttpStatus.FOUND); 
	}

	
	public ResponseEntity<ResponseStructure<CustomerDTO>> deleteCustomerById(long customerId) {
		customerDao.deleteCustomerById(customerId);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ACCEPTED.value(),
									"Customer Deleted...",
									null),
									HttpStatus.ACCEPTED); 
		
	}
	
	
	public ResponseEntity<ResponseStructure<CustomerDTO>> loginCustomer(String email,String password){
		
		Customer customer = customerDao.findCustomerByEmail(email);
		
		if(customer.getCustomerPassword().equals(password)) {
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CONTINUE.value(),
										"Login successfully",
										this.modelMapper.map(customer,CustomerDTO.class)),
										HttpStatus.CONTINUE);
		}
		
		throw new InvalidUsernameAndPasswordException("Invalid Username and password...!");
		
	}
	
	
}