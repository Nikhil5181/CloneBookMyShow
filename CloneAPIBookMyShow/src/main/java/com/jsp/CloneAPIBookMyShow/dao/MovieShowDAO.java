package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.MovieShow;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.ShowRepo;

@Repository
public class MovieShowDAO {

	@Autowired
	private ShowRepo showRepo;
	
	public MovieShow saveShow(MovieShow show) {
		return showRepo.save(show);
	}
	
	public MovieShow findShowById(long showId) {
		Optional<MovieShow> op = showRepo.findById(showId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given show id does not Exist...!");
	}
	
	public void deleteShowById(long showId) {
		findShowById(showId);
		showRepo.deleteById(showId);
	}
}

