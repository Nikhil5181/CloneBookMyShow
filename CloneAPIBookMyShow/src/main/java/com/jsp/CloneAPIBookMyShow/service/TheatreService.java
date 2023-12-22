package com.jsp.CloneAPIBookMyShow.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.AddressDAO;
import com.jsp.CloneAPIBookMyShow.dao.OwnerDAO;
import com.jsp.CloneAPIBookMyShow.dao.TheatreDAO;
import com.jsp.CloneAPIBookMyShow.dto.TheatreDTO;
import com.jsp.CloneAPIBookMyShow.entity.Address;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.entity.Theatre;
import com.jsp.CloneAPIBookMyShow.exception.DuplicateAddress;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedOwner;

@Service
public class TheatreService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TheatreDAO theatreDao;
	
	@Autowired
	private OwnerDAO ownerDao;
	
	@Autowired
	private AddressDAO addressDao;
	
//	SAVE
	public ResponseEntity<ResponseStructure<Theatre>> saveTheatre(long ownerId, long addressId, TheatreDTO theatre) {
		
		 Owner owner = ownerDao.findOwnerById(ownerId);
		 List<Theatre> theatreList = owner.getTheatre();
		 
		 if(theatreList.isEmpty())
			 theatreList = new ArrayList<Theatre>();
		 
		 Address address = addressDao.findAddressById(addressId);
		 /*I CHECK THIS ADDRESS ALREADY ASSIGN ANY THEATRE IF YES RAISE EXCEPTION*/
		 if(address.getTheatre()!=null) {
			 throw new DuplicateAddress("Given address already assigned by another theatre...");
		 }
		 Theatre theatree = this.modelMapper.map(theatre,Theatre.class);
		 theatree.setOwner(owner);
		 theatree.setAddress(address);
		 address.setTheatre(theatree);//address
		 theatreList.add(theatree);
		 
		 return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
				 											"Theatre Successfully Added..",
				 											theatreDao.saveTheatre(theatree)),
				 											HttpStatus.CREATED);	 
	}

//	UPDATE
	public ResponseEntity<ResponseStructure<Theatre>> updateTheatre(long theatreId, long ownerId, TheatreDTO theatre) {
		
		//FIRST I CHECK GIVEN OWNERID HAS THIS THEATRE OR NOT 
		
		Theatre oldTheatre = theatreDao.findTheatreById(theatreId);
				//IF THEATRE IS PRESENT MEANS OWNER HAS PERMISSION TO UPDATE THEARE INFORMATION
			if(oldTheatre.getOwner().getOwnerId() == ownerId) {
				Theatre theatree = this.modelMapper.map(theatre,Theatre.class);
				theatree.setTheatreId(theatreId);
				theatree.setOwner(oldTheatre.getOwner());
				theatree.setAddress(oldTheatre.getAddress());
				theatree.setScreens(oldTheatre.getScreens());
				theatree.setShow(oldTheatre.getShow());
				
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(),
																	"Theatre Information successfully updated....",
																	theatreDao.saveTheatre(theatree)),
																	HttpStatus.OK);	
			}
		throw new UnauthorizedOwner("This owner does not have permission to update Theatre Information....");	
	}
	

//	FIND
	public ResponseEntity<ResponseStructure<Theatre>> findTheatreById(long theatreId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
															"Theatre found...",
															theatreDao.findTheatreById(theatreId)),
															HttpStatus.FOUND);
	}
	

//	FIND-NAME
	public ResponseEntity<ResponseStructure<List<Theatre>>> findTheatreByName(String theatreName) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
															"Theatre found...",
															theatreDao.findTheaterByName(theatreName)),
															HttpStatus.FOUND);
	}
	

//	DELETE
	public ResponseEntity<ResponseStructure<Theatre>> deleteTheatreById(long theatreId, long ownerId) {
			//SAME AS UPDATE LOGIC
		Theatre theatre =theatreDao.findTheatreById(theatreId);
		
				if(theatre.getOwner().getOwnerId() == ownerId) {
					//RELATION BET'N OWNER AND THEATER I.E REASON FIRST REMOVE THIS RELATION INSIDE THE OWNER
					//IF YOU NOT REMOVE WE WILL GET EXCEPTION
					theatre.setOwner(null);
//					theatre.setAddress(null);
					/*WE NEED NOT BE REMOVE RELATION BET'N MOVIESHOW , SCREEN
					 * BECAUSE WITHOUT THEATRE THEY ARE NOT EXIST */
					
					theatreDao.deleteTheatreById(theatreId);
					return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ACCEPTED.value(),
																		"Theatre Deleted...",
																		null),
																		HttpStatus.ACCEPTED);
				}
		
			throw new UnauthorizedOwner("This owner does not have permission to delete Theatre Information....");
	}
}