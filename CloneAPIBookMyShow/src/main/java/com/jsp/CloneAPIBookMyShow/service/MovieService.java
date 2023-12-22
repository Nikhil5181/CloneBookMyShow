package com.jsp.CloneAPIBookMyShow.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.MovieDAO;
import com.jsp.CloneAPIBookMyShow.dao.ProductionHouseDAO;
import com.jsp.CloneAPIBookMyShow.dto.MovieDTO;
import com.jsp.CloneAPIBookMyShow.entity.Movie;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.entity.ProductionHouse;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedOwner;

@Service
public class MovieService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MovieDAO movieDao;
	
	@Autowired
	private ProductionHouseDAO productionDao;
	
//	SAVE
	public ResponseEntity<ResponseStructure<Movie>> saveMovie(MovieDTO movie , long productionId,long ownerId) {
		/*THIS MOVIE ALSO ADD INSIDE THE PRODUCTION HOUSE SO WE NEED TO CHECK WHO OWNER WILL ADD THE MOVIE IN PRODUCTIONHOUSE*/
		
		ProductionHouse productionHouse = productionDao.findProductionById(productionId);
		List<Movie> movieList = productionHouse.getMovies();
		
		if(productionHouse.getOwner().getOwnerId() == ownerId) {
		
				Movie movieEntity = this.modelMapper.map(movie, Movie.class);
				
				if(movieList.isEmpty())
					movieList = new ArrayList<>();
				
				movieList.add(movieEntity);
				
				productionHouse.setMovies(movieList);
				
				movieEntity.setProductionHouse(productionHouse);
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),"Movie Successfully saved...",
			    		 												movieDao.saveMovie(movieEntity)),HttpStatus.CREATED);
		}
		throw new UnauthorizedOwner("Owner does not have permission to add movies indside production house....");
	}

//	UPDATE
	public ResponseEntity<ResponseStructure<Movie>> updateMovie(MovieDTO movie, long movieId, long ownerId) {
		//CHECK PRODUCTIONHOUSE ?->THIS MOVIE THAT PRODUCTIONHOUSE OR NOT 
		//IF YOU CHECK PH THEN YOU NEED TO CHECK THIS PRODUCTIONHOUSE OWNER ALSO
			
			Movie oldMovie = movieDao.findMovieById(movieId);
			Owner owner = oldMovie.getProductionHouse().getOwner();
			
			if(owner.getOwnerId() == ownerId) {
				
				Movie movieEntity = this.modelMapper.map(movie,Movie.class);
				movieEntity.setMovieId(movieId);
				movieEntity.setProductionHouse(oldMovie.getProductionHouse());
				
				return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
																	"Movie Successfully updated...",
																	movieDao.saveMovie(movieEntity)),
																	HttpStatus.CREATED);	
				
			}
			throw new UnauthorizedOwner("Owner does not have permission to update movie information.....");
	}

//	FIND
	public ResponseEntity<ResponseStructure<Movie>> findMovieByName(String movieName) {	
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
										"Movie Found..",
										movieDao.findMovieByName(movieName)),
										HttpStatus.FOUND);
	}

//	DELETE
	public ResponseEntity<ResponseStructure<Movie>> deleteMovieById(long movieId, long ownerId) {
		//SAME PRODUCTIONHOUSE-> MOVIE ->OWNER
		
		Movie movie = movieDao.findMovieById(movieId);
		Owner owner = movie.getProductionHouse().getOwner();
		if(owner.getOwnerId() == ownerId) {
			movie.setProductionHouse(null);//REMOVE CONNECTION
			
			movieDao.deleteMovieById(movieId);
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.ACCEPTED.value(),
																"Movie deleted..",
																null),
																HttpStatus.FOUND);
		}
		throw new UnauthorizedOwner("Owner does not have permission to delete this movie...."); 
	}
}