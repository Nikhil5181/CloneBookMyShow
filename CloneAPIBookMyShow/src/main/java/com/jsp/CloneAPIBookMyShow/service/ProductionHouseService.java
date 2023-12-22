package com.jsp.CloneAPIBookMyShow.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.OwnerDAO;
import com.jsp.CloneAPIBookMyShow.dao.ProductionHouseDAO;
import com.jsp.CloneAPIBookMyShow.dto.ProductionHouseDTO;
import com.jsp.CloneAPIBookMyShow.entity.Owner;
import com.jsp.CloneAPIBookMyShow.entity.ProductionHouse;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedOwner;

@Service
public class ProductionHouseService {

	@Autowired
	private ProductionHouseDAO productionDao;

	@Autowired
	private OwnerDAO ownerDao;

	@Autowired
	private ModelMapper modelMapper;

//	SAVE
	public ResponseEntity<ResponseStructure<ProductionHouse>> saveProduction(ProductionHouseDTO production,
			long ownerId) {

		// this activity return owner entity object
		Owner owner = ownerDao.findOwnerById(ownerId);

		List<ProductionHouse> productionList = owner.getProductionHouse();

		if (productionList.isEmpty())
			productionList = new ArrayList<ProductionHouse>();

		ProductionHouse productionHouse = this.modelMapper.map(production, ProductionHouse.class);

		productionList.add(productionHouse);

		productionHouse.setOwner(owner);
		owner.setProductionHouse(productionList);

		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(),
				"ProductionHouse Created Successfully", productionDao.saveProduction(productionHouse)),
				HttpStatus.CREATED);

	}

//	UPDATE
	public ResponseEntity<ResponseStructure<ProductionHouse>> updateProduction(ProductionHouseDTO production,
			long productionId, long ownerId) {

		// first find production house
		ProductionHouse oldProductionHouse = productionDao.findProductionById(productionId);

		if (oldProductionHouse.getOwner().getOwnerId() == ownerId) {

			ProductionHouse productionHouse = this.modelMapper.map(production, ProductionHouse.class);
			// set old values like movie owner
			productionHouse.setProductionHouseId(productionId);
			productionHouse.setMovies(oldProductionHouse.getMovies());
			productionHouse.setOwner(oldProductionHouse.getOwner());

			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(), "ProductionHouse updated...",
					productionDao.saveProduction(productionHouse)), HttpStatus.OK);
		}
		// if productionHouse not present in provided ownerId means this owner is not
		// authorized
		throw new UnauthorizedOwner("owner does not have permission to update ProductionHouse... ");
	}

//	FIND
	public ResponseEntity<ResponseStructure<ProductionHouse>> findProductionById(long productionId) {
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(), "ProductionHouse found",
				productionDao.findProductionById(productionId)), HttpStatus.FOUND);
	}

//	DELETE
	public ResponseEntity<ResponseStructure<ProductionHouse>> deleteProductionById(long productionId, long ownerId) {

		// first check authorized owner or not for delete production
		// find
		ProductionHouse house = productionDao.findProductionById(productionId);
		// check owner has productionHouse or not if have then delete
		if (house.getOwner().getOwnerId() == ownerId) {
			// we can't perform delete operation directly because owner and production have
			// relation
			// first remove that owner connection in production / production->owner
			house.setOwner(null);
			// then perform delete operation..
			productionDao.deleteProductionById(productionId);
			return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.OK.value(), 
							"ProductionHouse deleted...", null), HttpStatus.OK);
		}
		throw new UnauthorizedOwner("owner do not have permission to delete productionHouse...");
	}
}