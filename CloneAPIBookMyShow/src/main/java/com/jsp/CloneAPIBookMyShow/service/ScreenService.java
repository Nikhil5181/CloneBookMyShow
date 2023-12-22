package com.jsp.CloneAPIBookMyShow.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.ScreenDAO;
import com.jsp.CloneAPIBookMyShow.dao.TheatreDAO;
import com.jsp.CloneAPIBookMyShow.dto.ScreenDTO;
import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.entity.Seat;
import com.jsp.CloneAPIBookMyShow.entity.Theatre;
import com.jsp.CloneAPIBookMyShow.enums.ScreenAvailability;
import com.jsp.CloneAPIBookMyShow.enums.ScreenStatus;
import com.jsp.CloneAPIBookMyShow.enums.SeatStatus;
import com.jsp.CloneAPIBookMyShow.enums.SeatType;
import com.jsp.CloneAPIBookMyShow.exception.ScreenNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.SeatsTotalGreaterThanActualSeat;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedOwner;

@Service
public class ScreenService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ScreenDAO screenDao;

	@Autowired
	private TheatreDAO theatreDao;
	

	
	public void addSeat(int noOfSeat, Screen screen, SeatType seatType, List<Seat> l) {
		for (int i = noOfSeat; i > 0; i--) {
			Seat seat = new Seat();
			seat.setScreen(screen);
			seat.setSeatType(seatType);
			seat.setSeatStatus(SeatStatus.AVAILABLE);
			l.add(seat);
		}
	}
	

//	SAVE
	public ResponseEntity<ResponseStructure<Screen>> saveScreen(long ownerId, long theatreId, ScreenDTO screen) {

		/* WE WILL STORE SEAT AT THE TIME SCREEN ADD. */
		Theatre theatre = theatreDao.findTheatreById(theatreId);

		if (theatre.getOwner().getOwnerId() == ownerId) {
			List<Screen> screenList = theatre.getScreens();

			if (screenList.isEmpty())
				screenList = new ArrayList<>();

			// CONVERT DTO TO ENTITY
			Screen screenEntity = this.modelMapper.map(screen, Screen.class);

			// ADDED SEAT
			List<Seat> seats = new ArrayList<>();
			addSeat(screen.getNoOfClassicSeats(), screenEntity, SeatType.CLASSIC, seats);
			addSeat(screen.getNoOfPlatinumSeats(), screenEntity, SeatType.PLATINUM, seats);
			addSeat(screen.getNoOfGoldSeats(), screenEntity, SeatType.GOLD, seats);

			screenEntity.setSeats(seats);
			// AT THE TIME SCREEN CREATED NOT ALLOTED ANY SHOW AND IT IS AVAILABLE
			screenEntity.setScreenAvailable(ScreenAvailability.NOT_ALLOTED);
			screenEntity.setScreenStatus(ScreenStatus.AVAILABLE);
			screenEntity.setTheatre(theatre);	
			screenList.add(screenEntity);
			theatre.setScreens(screenList);
			

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(), "Screen added...",
					screenDao.saveScreen(screenEntity)), HttpStatus.CREATED);
		}
		throw new UnauthorizedOwner("Owner does not have permission to add Screen this Theatre...");
	}

//	UPDATE
	public ResponseEntity<ResponseStructure<Screen>> updateScreen(long ownerId, long theatreId, long screenId,
			ScreenDTO screen) {
		/*
		 * IN THIS UPDATE PART FIRST WE CHECK OWNER HAS THIS THEATRE OR NOT IF THEATRE
		 * YES THEN I CHECK THIS THEATRE HAS THIS SCREEN ARE OR NOT IF YES THEN I
		 * PERFORM UPDATE OPERATION
		 */

		Theatre theatre = theatreDao.findTheatreById(theatreId);
		List<Screen> screenList = theatre.getScreens();
		// THIS LINE CHECK OWNER
		if (theatre.getOwner().getOwnerId() == ownerId) {
			Screen oldScreen = screenDao.findScreenById(screenId);
			// HERE I CHECK THIS SCREEN EXIST ARE OR NOT IN THEATRE
			if (screenList.contains(oldScreen)) {
				
				Screen newScreen = this.modelMapper.map(screen, Screen.class);
			
				//check update seat 
				if(screen.getNoOfClassicSeats() == 0 && screen.getNoOfGoldSeats() == 0 && screen.getNoOfPlatinumSeats() == 0) {

				newScreen.setScreenId(screenId);
				newScreen.setNoOfClassicSeats(oldScreen.getNoOfClassicSeats());
				newScreen.setNoOfGoldSeats(oldScreen.getNoOfGoldSeats());
				newScreen.setNoOfPlatinumSeats(oldScreen.getNoOfPlatinumSeats());
				newScreen.setSeats(oldScreen.getSeats());
				newScreen.setTheatre(theatre);
				 
				} 
				else {
				
					int totalSeat = oldScreen.getNoOfClassicSeats() + oldScreen.getNoOfGoldSeats() + oldScreen.getNoOfPlatinumSeats();
					
					int updateSeat = screen.getNoOfClassicSeats() + screen.getNoOfGoldSeats() + screen.getNoOfPlatinumSeats();
					
					if(updateSeat == totalSeat) {
					
						List<Seat> listOfSeat = oldScreen.getSeats();
						
						for(int i = 0 ; i < totalSeat ; i++) {
							
						   Seat seat = listOfSeat.get(i);
						   
						   if(i < screen.getNoOfClassicSeats())
							   seat.setSeatType(SeatType.CLASSIC);
						   else if(i < (screen.getNoOfClassicSeats() + screen.getNoOfPlatinumSeats())) 
							   seat.setSeatType(SeatType.PLATINUM);
						   else
							   seat.setSeatType(SeatType.GOLD);
						   
						   seat.setSeatStatus(SeatStatus.AVAILABLE);
						   listOfSeat.add(seat);	
						} 
						
						
				 		newScreen.setScreenId(screenId);
						newScreen.setNoOfClassicSeats(screen.getNoOfClassicSeats());
						newScreen.setNoOfGoldSeats(screen.getNoOfGoldSeats());
						newScreen.setNoOfPlatinumSeats(screen.getNoOfPlatinumSeats());
						newScreen.setSeats(listOfSeat);
						newScreen.setTheatre(theatre);
						
						
						
					}//update if 
					else 
						throw new SeatsTotalGreaterThanActualSeat("Theatre Seat not count greatre than actual count...!");
		
				}//else block of update seat if
				
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(), "Screen Updated...",
                                            						screenDao.saveScreen(newScreen)),// PERFORM UPDATE OPERATION
																	HttpStatus.OK);
				
			}//screen check if
			throw new ScreenNotFoundException("You Specified Screen not present this theatre..");
		}//owner permission if
		throw new UnauthorizedOwner("Owner does not have permission to update Screen this Theatre...");
	}

//	FIND
	public ResponseEntity<ResponseStructure<Screen>> findScreenById(long screenId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(), "Screen Found...",
				screenDao.findScreenById(screenId)), HttpStatus.FOUND);
	}

	
//	DELETE
	public ResponseEntity<ResponseStructure<Screen>> deleteScreenById(long ownerId, long theatreId, long screenId) {

		Theatre theatre = theatreDao.findTheatreById(theatreId);
		List<Screen> screenList = theatre.getScreens();

		if (theatre.getOwner().getOwnerId() == ownerId) {
			Screen screen = screenDao.findScreenById(screenId);

			if (screenList.contains(screen)) {
				screenList.remove(screen); // REMOVE RELATION BET'N THEATRE AND SCREEN
			
				screen.setTheatre(null);
				theatre.setScreens(screenList);
				screenDao.deleteScreenById(screenId);
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
																	"Screen Deleted...", 
																	null),
																	HttpStatus.CREATED);

			}
			throw new ScreenNotFoundException("You Specified Screen not Present this theatre..");
		}
		throw new UnauthorizedOwner("Owner does not have permission to delete Screen this Theatre...");
	}

}