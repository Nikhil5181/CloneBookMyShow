package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.entity.Seat;
import com.jsp.CloneAPIBookMyShow.enums.SeatStatus;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.SeatRepo;

@Repository
public class SeatDAO {

	@Autowired
	private SeatRepo seatRepo;
	
	public Seat saveSeat(Seat seat) {
		return seatRepo.save(seat);
	}
	
	public Seat findSeatById(long seatId) {
		Optional<Seat> op = seatRepo.findById(seatId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given Seat id does not exist...!");
	}
	
	public void deleteSeatById(long seatId) {
		findSeatById(seatId);
		seatRepo.deleteById(seatId);
	}
	
	public long countSeatBySeatStatus(SeatStatus seatStatus ,Screen screen) {
		return seatRepo.countSeatBySeatStatusAndScreen(seatStatus,screen);
	}
	
	public long countSeatByScreenId(Screen screen) {
		return seatRepo.countSeatByScreen(screen);
	}
	
}