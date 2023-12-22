package com.jsp.CloneAPIBookMyShow.controller;

import java.util.List;

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
import com.jsp.CloneAPIBookMyShow.dto.TheatreDTO;
import com.jsp.CloneAPIBookMyShow.entity.Theatre;
import com.jsp.CloneAPIBookMyShow.service.TheatreService;

@RestController
@RequestMapping("/theatre")
public class TheatreController {

	@Autowired
	private TheatreService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<Theatre>> saveTheatre(@RequestParam long ownerId,@RequestParam long addressId ,@Validated @RequestBody TheatreDTO theatre){
		return service.saveTheatre(ownerId,addressId,theatre);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Theatre>> updateTheatre(@RequestParam long theatreId , @RequestParam long ownerId ,@Validated @RequestBody TheatreDTO theatre){
		return service.updateTheatre(theatreId,ownerId,theatre);
	}
	
	@GetMapping("/id")
	public ResponseEntity<ResponseStructure<Theatre>> findTheatreById(@RequestParam long theatreId){
		return service.findTheatreById(theatreId);
	}
	
	@GetMapping("/name")
	public ResponseEntity<ResponseStructure<List<Theatre>>> findTheatreByName(@RequestParam String theatreName){
		return service.findTheatreByName(theatreName);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<Theatre>> deleteTheatreById(@RequestParam long theatreId , @RequestParam long ownerId){
		return service.deleteTheatreById(theatreId,ownerId);
	}
	
	
}
