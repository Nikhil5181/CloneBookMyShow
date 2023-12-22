package com.jsp.CloneAPIBookMyShow.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.MovieDAO;
import com.jsp.CloneAPIBookMyShow.dao.MovieShowDAO;
import com.jsp.CloneAPIBookMyShow.dao.OwnerDAO;
import com.jsp.CloneAPIBookMyShow.dao.ScreenDAO;
import com.jsp.CloneAPIBookMyShow.dao.TheatreDAO;
import com.jsp.CloneAPIBookMyShow.dto.MovieShowDTO;
import com.jsp.CloneAPIBookMyShow.entity.Movie;
import com.jsp.CloneAPIBookMyShow.entity.MovieShow;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.entity.Theatre;
import com.jsp.CloneAPIBookMyShow.enums.ScreenAvailability;
import com.jsp.CloneAPIBookMyShow.exception.MovieNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.ScreenAlreadyAlloted;
import com.jsp.CloneAPIBookMyShow.exception.ScreenNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedOwner;

@Service
public class MovieShowService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MovieShowDAO movieShowDao;
	
	@Autowired
	private TheatreDAO theatreDao;
	
	@Autowired
	private MovieDAO movieDao;
	
	@Autowired
	private ScreenDAO screenDao;
	
	@Autowired
	private OwnerDAO ownerDao;
	
	
//	SAVE
	public ResponseEntity<ResponseStructure<MovieShow>> saveMovie(long theatreId, long ownerId, MovieShowDTO movieDto) {
	
		/*FIRST CHECK GIVEN OWNER HAVE PERMISSION TO CREATE SHOW FOR THIS THEATRE BECAUSE WE CREATE SHOW FOR THIS THEATRE*/
		Owner owner = ownerDao.findOwnerById(ownerId);
		Theatre theatre = theatreDao.findTheatreById(theatreId);
		List<MovieShow> listMovieShow = theatre.getShow();
		
		if(theatre.getOwner().getOwnerId() == ownerId) {
			
				if(listMovieShow.isEmpty())
					listMovieShow = new ArrayList<>();
			
				//AND GET MOVIE ID AND SCREEN ID FROM THE GIVEN OBJECT OF MOVIESHOW
				Movie movie = movieDao.findMovieById(movieDto.getMovieId());
				Screen screen = screenDao.findScreenById(movieDto.getScreenId());
				
				//WE CHECK THIS MOVIE AND SCREEN PRESENT INSIDE THE OWNER PRODUCTIONHOUSE AND THEATRE			
				if(owner.getProductionHouse().contains(movie.getProductionHouse())) {
			
					if(theatre.getScreens().contains(screen)) {
						
						//CHECK SCREEN IS ALREADY ALLOTED ANOTHER SHOW OR NOT
						if(screen.getScreenAvailable().equals(ScreenAvailability.ALLOTED))
							throw new ScreenAlreadyAlloted("Screen already alloted another Show...");
						
						MovieShow movieEntity = this.modelMapper.map(movieDto,MovieShow.class);
						
						String gener = movie.getGenre1()+", "+movie.getGenre2()+", "+movie.getGenre3();
						 
						movieEntity.setMovieName(movie.getMovieName());
						movieEntity.setShowLocation(theatre.getAddress().toString());
						movieEntity.setGenre(gener);
						movieEntity.setMovieDescription(movie.getMovieDescription());
						
						//I set the MovieShow duration start of MovieShow + movie duration
						
						LocalTime duration = movieDto.getShowStartTime().plusHours(movie.getMovieDuration().getHour());
						movieEntity.setMovieDuration(duration.plusMinutes(movie.getMovieDuration().getMinute()).plusMinutes(9));
						
						
						movieEntity.setMovieLanguage(movie.getLanguage());		
						movieEntity.setScreenName(screen.getScreenName());
						movieEntity.setTheatre(theatre);
						
						listMovieShow.add(movieEntity);
						
						theatre.setShow(listMovieShow);
						//IF SCREEN ALLOTED CHANGE AVAILABILITY TO ALLOTED
						screen.setScreenAvailable(ScreenAvailability.ALLOTED);
						
						
						return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
													"MovieShow Created..",
													movieShowDao.saveShow(movieEntity)),
													HttpStatus.CREATED);						
					}
					throw new ScreenNotFoundException("Screen not found to gave theatre so you can't create movieshow this theatre Screen...please choose this theatre screen id .. ");
				}
				throw new MovieNotFoundException("This movie not present inside the productionhouse of this owner you can't create movieshow this movie..."); 
		}
			throw new UnauthorizedOwner("This owner does not have permission to create show for that theatre...");
	}


//	UPDATE
	public ResponseEntity<ResponseStructure<MovieShow>> updateMovieShow(long movieShowId, long ownerId,MovieShowDTO movieShowDto) {
		
		List<Theatre> listOfTheatre= ownerDao.findOwnerById(ownerId).getTheatre();
		MovieShow oldMovieShow = movieShowDao.findShowById(movieShowId);
		
			if(listOfTheatre.contains(oldMovieShow.getTheatre())) {
				
				//IN SHOW WE UPDATE SHOW INFO NOT MOVIE,SCREEN BECAUSE CHANGE MOVIE MEANS YOU CREATE NEW SHOW 
				MovieShow newMovieShow = this.modelMapper.map(movieShowDto,MovieShow.class);
				
				//SET OLD OBJECT VALUE TO NEW OBJECT 
				newMovieShow.setShowId(oldMovieShow.getShowId());
				newMovieShow.setMovieId(oldMovieShow.getMovieId());
				newMovieShow.setMovieName(oldMovieShow.getMovieName());
				newMovieShow.setShowLocation(oldMovieShow.getShowLocation());
				newMovieShow.setGenre(oldMovieShow.getGenre());
				newMovieShow.setMovieDescription(oldMovieShow.getMovieDescription());
				newMovieShow.setMovieDuration(oldMovieShow.getMovieDuration());
				newMovieShow.setMovieLanguage(oldMovieShow.getMovieLanguage());
				newMovieShow.setScreenId(oldMovieShow.getScreenId());
				newMovieShow.setScreenName(oldMovieShow.getScreenName());
				newMovieShow.setTheatre(oldMovieShow.getTheatre());
				
				
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(),
											"MovieShow Updated Successfully", 
											movieShowDao.saveShow(newMovieShow)),
											HttpStatus.OK);	
			}
		
		throw new UnauthorizedOwner("This owner does not have permission to update this movieShow");	
	}

	
//	FIND
	public ResponseEntity<ResponseStructure<MovieShow>> findMovieShowById(long movieShowId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
									"Movie Show Founded..",
									movieShowDao.findShowById(movieShowId)),
									HttpStatus.FOUND);
	}

	
//  DELETE
	public ResponseEntity<ResponseStructure<MovieShow>> deleteMovieShowById(long movieShowId, long ownerId) {
	
		List<Theatre> listTheatre = ownerDao.findOwnerById(ownerId).getTheatre();
		MovieShow movieShow = movieShowDao.findShowById(movieShowId);
	
		if(listTheatre.contains(movieShow.getTheatre())){
			movieShow.setTheatre(null);
			
			/*WHEN WE DELETE THIS SHOW THEN WE NEED TO CHAGE SCREEN AVAILABILITY*/
			screenDao.findScreenById(movieShow.getScreenId()).setScreenAvailable(ScreenAvailability.NOT_ALLOTED);
			
			movieShowDao.deleteShowById(movieShowId);
			
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ACCEPTED.value(),
										"MovieShow Deleted... ",
										null),
										HttpStatus.ACCEPTED);
			
		}
		throw new UnauthorizedOwner("owner does not have permossion to delete this show...");
	}
}//CLASS