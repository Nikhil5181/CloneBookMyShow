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
import com.jsp.CloneAPIBookMyShow.dto.ProductionHouseDTO;
import com.jsp.CloneAPIBookMyShow.entity.ProductionHouse;
import com.jsp.CloneAPIBookMyShow.service.ProductionHouseService;

@RestController
@RequestMapping("/house")
public class ProductionHouseController {
	
	@Autowired
	private ProductionHouseService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<ProductionHouse>> saveProduction(@Validated @RequestBody ProductionHouseDTO production,@RequestParam long ownerId){
		return service.saveProduction(production,ownerId);
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<ProductionHouse>> updateProduction(@Validated @RequestBody ProductionHouseDTO  production, @RequestParam long productionId,@RequestParam  long ownerId){
		return service.updateProduction(production,productionId,ownerId);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<ProductionHouse>> findProductionById(@RequestParam long productionId){
		return service.findProductionById(productionId);
	}
	
	@DeleteMapping
	public ResponseEntity<ResponseStructure<ProductionHouse>> deleteProductionById(@RequestParam long productionId , @RequestParam long ownerId){
		return service.deleteProductionById(productionId,ownerId);
	}
}