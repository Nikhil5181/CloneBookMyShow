package com.jsp.CloneAPIBookMyShow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.OwnerDAO;
import com.jsp.CloneAPIBookMyShow.dto.OwnerDTO;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.exception.InvalidUsernameAndPasswordException;
import com.jsp.CloneAPIBookMyShow.exception.OwnerIdNotBeNull;

@Service
public class OwnerService {

	@Autowired
	private OwnerDAO ownerDao;
	
	//Convert entity to DTO layer  or we can use ModelMapper
	public OwnerDTO mapEntityToOwnerDTO(Owner owner) {
		return new OwnerDTO(owner.getOwnerId(),owner.getOwnerName(),owner.getOwnerPhoneNumber(),owner.getOwnerEmail());
	}
	
	//save
	public ResponseEntity<ResponseStructure<OwnerDTO>> saveOwner(Owner owner){		
	    return  new ResponseEntity<ResponseStructure<OwnerDTO>>(new ResponseStructure<OwnerDTO>(HttpStatus.CREATED.value(),
	    														"Owner Successfully saved...",
	    														mapEntityToOwnerDTO(ownerDao.saveOwner(owner))),
	    														HttpStatus.CREATED);
	}
	
	//update 
	public ResponseEntity<ResponseStructure<OwnerDTO>> updateOwner(Owner owner) {
		//first check owner is present or not given id i.e OWNER ID Should not be null/0
		
		if(owner.getOwnerId() > 0) {
			ownerDao.findOwnerById(owner.getOwnerId());
		
			return new ResponseEntity<ResponseStructure<OwnerDTO>>(new ResponseStructure<OwnerDTO>(HttpStatus.CREATED.value(),
																  "Owner Successfully updated...",
																  mapEntityToOwnerDTO(ownerDao.saveOwner(owner))),
																  HttpStatus.CREATED);
		}
		    
		throw new OwnerIdNotBeNull("Owner id should not be null please pass owner id..?");		
	}
	
	
	//find 
	public ResponseEntity<ResponseStructure<OwnerDTO>> findOwnerById(long ownerId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Owner Found..",
									mapEntityToOwnerDTO(ownerDao.findOwnerById(ownerId))),
									HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<OwnerDTO>> findByEmail(String email) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Data is found",
									mapEntityToOwnerDTO(ownerDao.findOwnerByEmail(email))),
									HttpStatus.FOUND);
	}
	
	
	public ResponseEntity<ResponseStructure<OwnerDTO>> findByPhoneNumber(long phoneNumber) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Data is found",
									mapEntityToOwnerDTO(ownerDao.findOwnerByPhoneNumber(phoneNumber))),
									HttpStatus.FOUND);
	}
	
	//delete 
	public ResponseEntity<ResponseStructure<OwnerDTO>> deleteOwnerById(long ownerId) {
			   ownerDao.deleteOwnerById(ownerId);
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ACCEPTED.value(),
									"Given owner id was deleted...",
									null),
									HttpStatus.ACCEPTED);
	}

	public ResponseEntity<ResponseStructure<OwnerDTO>> loginOwner(String ownerEmail, String ownerPassword) {
		
		Owner owner = ownerDao.findOwnerByEmail(ownerEmail);
		
		if(owner.getOwnerPassword().equals(ownerPassword)) {
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
															  "Login successfully Done...",
															  mapEntityToOwnerDTO(owner)),
															  HttpStatus.FOUND);
		}
		
		throw new InvalidUsernameAndPasswordException("Invalid Username and password...!");
	}	
}