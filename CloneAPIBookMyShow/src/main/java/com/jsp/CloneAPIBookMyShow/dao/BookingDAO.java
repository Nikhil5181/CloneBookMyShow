package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Booking;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.BookingRepo;

@Repository
public class BookingDAO {

	@Autowired
	BookingRepo bookingRepo;
	
	public Booking saveBooking(Booking booking) {
		return bookingRepo.save(booking);
	}
	
	public Booking findBookingById(long bookingId) {
		Optional<Booking> op = bookingRepo.findById(bookingId);
		if(op.isPresent())
			return  op.get();
		
		throw new IdNotFoundException("Booking not present you provided Id..!");
	}
	
	public void deleteBookingById(long bookingId) {
		findBookingById(bookingId);
		bookingRepo.deleteById(bookingId);
	}
}
