package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.ProductionHouse;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.ProductionHouseRepo;

@Repository
public class ProductionHouseDAO {

	@Autowired
	private ProductionHouseRepo productionRepo;
	
	//save
	public ProductionHouse saveProduction(ProductionHouse production) {
		return productionRepo.save(production);
	}
	
	//find
	public ProductionHouse findProductionById(long productionId) {
		Optional<ProductionHouse> op = productionRepo.findById(productionId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given production_house id does not exist...!");
	}
	
	//delete
	public void deleteProductionById(long productionId) {
		findProductionById(productionId);
		productionRepo.deleteById(productionId);
	}
}
