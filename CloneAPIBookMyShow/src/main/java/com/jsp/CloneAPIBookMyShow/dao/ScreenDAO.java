package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.ScreenRepo;

@Repository
public class ScreenDAO {

	@Autowired
	private ScreenRepo screenRepo;
	
	public Screen saveScreen(Screen screen) {
		return screenRepo.save(screen);
	}
	
	public Screen findScreenById(long screenId) {
		Optional<Screen> op = screenRepo.findById(screenId);
		if(op.isPresent())
			return op.get();
		
		throw new  IdNotFoundException("Given Screen id does not exist....!");
	}
	
	public void deleteScreenById(long ScreenId) {
		findScreenById(ScreenId);
		screenRepo.deleteById(ScreenId);
	}
}
