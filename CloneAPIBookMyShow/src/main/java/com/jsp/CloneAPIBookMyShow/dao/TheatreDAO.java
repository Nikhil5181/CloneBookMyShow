package com.jsp.CloneAPIBookMyShow.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Theatre;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.exception.TheatreNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.TheatreRepo;

@Repository
public class TheatreDAO {

	@Autowired
	private TheatreRepo theatreRepo;
	
	public Theatre saveTheatre(Theatre theatre) {
		return theatreRepo.save(theatre);
	}
	
	public Theatre findTheatreById(long theatreId) {
		Optional<Theatre> op = theatreRepo.findById(theatreId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given theatre id does not found...!");
	}
	
	public List<Theatre> findTheaterByName(String theatreName){
		List<Theatre> list = theatreRepo.findByTheaterName(theatreName);
		if(!list.isEmpty())
			return list;
		
		throw new TheatreNotFoundException("Given name theatre do not exist...!");
	}
	
	public void deleteTheatreById(long theatreId) {
		findTheatreById(theatreId);
		theatreRepo.deleteById(theatreId);
	}
}
