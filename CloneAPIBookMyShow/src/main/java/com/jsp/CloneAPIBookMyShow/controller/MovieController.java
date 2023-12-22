package com.jsp.CloneAPIBookMyShow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dto.MovieDTO;
import com.jsp.CloneAPIBookMyShow.entity.Movie;
import com.jsp.CloneAPIBookMyShow.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Movie>> saveMovie(@Validated @RequestBody MovieDTO movie, @RequestParam long productionId , @RequestParam long ownerId){
		return movieService.saveMovie(movie,productionId,ownerId);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Movie>> updateMovie(@Validated @RequestBody MovieDTO movie, @RequestParam long movieId,@RequestParam long ownerId){
		return movieService.updateMovie(movie,movieId,ownerId);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<Movie>> findMovieByName(@RequestParam String movieName){
		return movieService.findMovieByName(movieName);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<Movie>> deleteMovieById(@RequestParam long movieId ,@RequestParam long ownerId){
		return movieService.deleteMovieById(movieId,ownerId);
	}
	
}