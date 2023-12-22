package com.jsp.CloneAPIBookMyShow.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.CloneAPIBookMyShow.entity.Ticket;
import com.jsp.CloneAPIBookMyShow.exception.IdNotFoundException;
import com.jsp.CloneAPIBookMyShow.repository.TicketRepo;

@Repository
public class TicketDAO {

	@Autowired
	private TicketRepo ticketRepo;
	
	public Ticket saveTicket(Ticket ticket) {
		return ticketRepo.save(ticket);
	}
	
	public Ticket findTicketById(long ticketId) {
		Optional<Ticket> op = ticketRepo.findById(ticketId);
		if(op.isPresent())
			return op.get();
		
		throw new IdNotFoundException("Given ticket id does not exist..!");
	}
	
	public void deleteTicketById(long ticketId) {
		findTicketById(ticketId);
		ticketRepo.deleteById(ticketId);
	}
}
