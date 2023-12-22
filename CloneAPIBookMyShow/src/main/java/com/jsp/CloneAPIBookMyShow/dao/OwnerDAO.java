package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.exception.EmailNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.PhoneNumberNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.OwnerRepo;

@Repository
public class OwnerDAO {

	@Autowired
	private OwnerRepo ownerRepo;
	
	public Owner saveOwner(Owner owner) {
		return ownerRepo.save(owner);
	}
	
	public Owner findOwnerById(long ownerId) {
		Optional<Owner> op = ownerRepo.findById(ownerId);
		if(op.isPresent()) 
			return op.get();
		
		throw new IdNotFoundException("owner is not existed you provided Id...!");
	}
	
	
	public Owner findOwnerByEmail(String email) {
	     Owner owner = ownerRepo.findOwnerByOwnerEmail(email);
	     if(owner != null)
	    	 return owner;
	     
	     throw new EmailNotFoundException("Given Email owner not present....");
	}
	
	
	public Owner findOwnerByPhoneNumber(long mobile) {
		 Owner owner = ownerRepo.findOwnerByOwnerPhoneNumber(mobile);
		 if(owner != null)
			 return owner;
		 
		 throw new PhoneNumberNotFoundException("Given PhoneNumber owner not found....");
	}
	
	public void deleteOwnerById(long ownerId) {
		Owner owner = findOwnerById(ownerId);
		ownerRepo.deleteById(owner.getOwnerId());
	}

}