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
import com.jsp.CloneAPIBookMyShow.dto.ScreenDTO;
import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.service.ScreenService;

@RestController
@RequestMapping("/screen")
public class ScreenController {

	@Autowired
	private ScreenService service;

	@PostMapping
	public ResponseEntity<ResponseStructure<Screen>> saveScreen(@RequestParam long ownerId,
			@RequestParam long theatreId, @Validated @RequestBody ScreenDTO screen) {
		return service.saveScreen(ownerId, theatreId, screen);
	}

	@PutMapping
	public ResponseEntity<ResponseStructure<Screen>> updateScreen(@RequestParam long ownerId,
			@RequestParam long theatreId, @RequestParam long screenId, @Validated @RequestBody ScreenDTO screen) {

		return service.updateScreen(ownerId, theatreId, screenId, screen);
	}

	@GetMapping
	public ResponseEntity<ResponseStructure<Screen>> findScreenById(@RequestParam long screenId) {
		return service.findScreenById(screenId);
	}

	@DeleteMapping
	public ResponseEntity<ResponseStructure<Screen>> deleteScreenById(@RequestParam long ownerId,
			@RequestParam long theatreId, @RequestParam long screenId) {
		return service.deleteScreenById(ownerId, theatreId, screenId);
	}
}