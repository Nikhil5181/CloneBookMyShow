package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Movie;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.MovieNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.MovieRepo;

@Repository
public class MovieDAO {

	@Autowired
	private MovieRepo movieRepo;
	
	public Movie saveMovie(Movie movie) {
		return movieRepo.save(movie);
	}
	
	public Movie findMovieById(long movieId) {
		Optional<Movie> op = movieRepo.findById(movieId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given Movie id does not Found..!");
	}
	
	public Movie findMovieByName(String movieName) {
		Movie m = movieRepo.findMovieBymovieName(movieName);
		if(m != null)
			return m;
		throw new MovieNotFoundException("Given name movie do not exist...!");
	}
	
	public void deleteMovieById(long movieId) {
		findMovieById(movieId);
		movieRepo.deleteById(movieId);
	}
}
