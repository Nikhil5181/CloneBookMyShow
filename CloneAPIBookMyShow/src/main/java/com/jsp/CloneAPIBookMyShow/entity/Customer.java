package com.jsp.CloneAPIBookMyShow.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long customerId;
	
	@Pattern(regexp = "[\\D\\s]*" , message = "Invalid Name..")
	private String customerName;
	
	@Column(unique = true)
	@Min(value = 6666666666l, message = "invalid mobile number..")
	@Max(value = 9999999999l, message = "invalid mobile number..")
	private long customerPhone;
	
	@Email(message = "Invalid Email...")
	private String customerEmail;
	
	@Pattern(regexp = "[\\w]*[\\W]*[\\w]*" , message = "Password must contain at least number,Character,Special Symbol")
	@Length(min = 8 , message  = "minimum 8 character")
	private String customerPassword;
	
	//unidirectional
	@OneToMany
	private List<Seat> seats;
}
