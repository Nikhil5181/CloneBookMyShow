package com.jsp.CloneAPIBookMyShow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ownerId;
	
	@NotBlank(message = "Owner_Name cant't be null....")
	@Pattern(regexp = "[a-zA-Z\\s]*" , message = "Invalid Name..")
	private String ownerName;
	
	@Column(unique = true)
	@Min(value = 6000000000l, message = "invalid number number can't be less than 6000000000")
	@Max(value = 9999999999l, message = "invalid number number can't be Greater than 9999999999")
	private long ownerPhoneNumber;
	
	@Column(unique = true)
	@Email(message = "INVALID EMAIL")
	private String ownerEmail;
	
	@Length(min = 8 , message = "password can't LESS THAN 8 CHAR")
	@Pattern(regexp = "[\\w]*[\\W]*[\\w]*" , message = "Combination of Number,Character and Special Character")
	private String ownerPassword;
	
	@JsonIgnore
	@OneToMany(mappedBy = "owner" , cascade = CascadeType.REMOVE)
	private List<ProductionHouse> productionHouse;
	
	@OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Theatre> theatre;
	
}
