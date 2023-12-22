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
import com.jsp.CloneAPIBookMyShow.dto.MovieShowDTO;
import com.jsp.CloneAPIBookMyShow.entity.MovieShow;
import com.jsp.CloneAPIBookMyShow.service.MovieShowService;

@RestController
@RequestMapping("/movieShow")
public class MovieShowController {

	@Autowired
	private MovieShowService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<MovieShow>> saveMovieShow(@RequestParam long theatreId,@RequestParam long ownerId,@Validated @RequestBody MovieShowDTO movieShow){
		return service.saveMovie(theatreId,ownerId,movieShow);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<MovieShow>> updaeMovieShow(@RequestParam long movieShowId , @RequestParam long ownerId,@Validated @RequestBody MovieShowDTO movieShow){
		return service.updateMovieShow(movieShowId,ownerId,movieShow);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<MovieShow>> findMovieShowById(@RequestParam long movieShowId){
		return service.findMovieShowById(movieShowId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<MovieShow>> deleteMovieShowById(@RequestParam long movieShowId , @RequestParam long ownerId){
		return service.deleteMovieShowById(movieShowId,ownerId);
	}
	
}