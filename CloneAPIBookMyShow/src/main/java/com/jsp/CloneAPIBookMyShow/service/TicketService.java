package com.jsp.CloneAPIBookMyShow.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.CloneAPIBookMyShow.config.ResponseStructure;
import com.jsp.CloneAPIBookMyShow.dao.CustomerDAO;
import com.jsp.CloneAPIBookMyShow.dao.MovieShowDAO;
import com.jsp.CloneAPIBookMyShow.dao.ScreenDAO;
import com.jsp.CloneAPIBookMyShow.dao.SeatDAO;
import com.jsp.CloneAPIBookMyShow.dao.TicketDAO;
import com.jsp.CloneAPIBookMyShow.entity.Booking;
import com.jsp.CloneAPIBookMyShow.entity.Customer;
import com.jsp.CloneAPIBookMyShow.entity.MovieShow;
import com.jsp.CloneAPIBookMyShow.entity.Screen;
import com.jsp.CloneAPIBookMyShow.entity.Seat;
import com.jsp.CloneAPIBookMyShow.entity.Ticket;
import com.jsp.CloneAPIBookMyShow.enums.BookingStatus;
import com.jsp.CloneAPIBookMyShow.enums.ScreenStatus;
import com.jsp.CloneAPIBookMyShow.enums.SeatStatus;
import com.jsp.CloneAPIBookMyShow.enums.ShowStatus;
import com.jsp.CloneAPIBookMyShow.enums.TicketStatus;
import com.jsp.CloneAPIBookMyShow.exception.SeatAlreadyBooked;
import com.jsp.CloneAPIBookMyShow.exception.ShowAlreadyCancelled;
import com.jsp.CloneAPIBookMyShow.exception.ShowAlreadyClosed;
import com.jsp.CloneAPIBookMyShow.exception.ShowHasAlreadyStrated;
import com.jsp.CloneAPIBookMyShow.exception.ShowHasCancelledOrClosed;
import com.jsp.CloneAPIBookMyShow.exception.ShowIsHousefull;
import com.jsp.CloneAPIBookMyShow.exception.TicketAlreadyCancelled;
import com.jsp.CloneAPIBookMyShow.exception.TicketAlreadyExpired;
import com.jsp.CloneAPIBookMyShow.exception.UnauthorizedCustomer;

@Service
public class TicketService {

	@Autowired
	private CustomerDAO customerDao;

	@Autowired
	private MovieShowDAO movieShowDao;

	@Autowired
	private SeatDAO seatDao;

	@Autowired
	private TicketDAO ticketDao;

	@Autowired
	private ScreenDAO screenDao;

//	SAVE
	public ResponseEntity<ResponseStructure<Ticket>> saveTicket(long customerId, long movieShowId, long[] seatId) {

		double totalPrice = 0;
		
		// FIRST CHECK CUSTOMER AND MOVIESHOW ARE PRESENT OR NOT
		Customer customer = customerDao.findCustomerById(customerId);
		List<Seat> listSeat = customer.getSeats();

		MovieShow movieShow = movieShowDao.findShowById(movieShowId);

		Ticket ticket = new Ticket();
		List<Booking> listBooking = ticket.getBookings();

		// GET SEATS ONE BY ONE AND CHECK THIS SEAT PRESENT OR NOT
		for (long id : seatId) {
			Seat seat = seatDao.findSeatById(id);

			// TO CHECK THIS SHOW ALL TICKET ARE BOOKED OR NOT IF BOOKED CHANGE STATUS HOUSEFULL
			if (seatDao.countSeatByScreenId(screenDao.findScreenById(movieShow.getScreenId())) == (seatDao
					.countSeatBySeatStatus(SeatStatus.BOOKED, screenDao.findScreenById(movieShow.getScreenId()))
					+ seatDao.countSeatBySeatStatus(SeatStatus.BLOCKED,
							screenDao.findScreenById(movieShow.getScreenId())))) {

				Screen screen = screenDao.findScreenById(movieShow.getScreenId());
							     screen.setScreenStatus(ScreenStatus.HOUSEFULL);
							     screenDao.saveScreen(screen);
			}

			double price = 0;
			// CHECK MOVIESHOW HOUSEFULL OR NOT
			if (!screenDao.findScreenById(movieShow.getScreenId()).getScreenStatus().equals(ScreenStatus.HOUSEFULL)) {

				if (seat.getSeatStatus().equals(SeatStatus.AVAILABLE)) {
					// CHECK THIS SHOW ACTIVE OR NOT
					if (movieShow.getShowStatus().equals(ShowStatus.ACTIVE) || movieShow.getShowStatus().equals(ShowStatus.ONGOING)) {

						// CHANGE SEAT STATUS
						seat.setSeatStatus(SeatStatus.BOOKED);
						// CREATE BOOKING
						Booking booking = new Booking();
						booking.setBookingFromTime(movieShow.getShowStartTime());
						booking.setBookingTillTime(movieShow.getShowEndTime());
						booking.setSeatId(id);
						booking.setSeatType(seat.getSeatType());
						booking.setBookingStatus(BookingStatus.ACTIVE);

						switch (seat.getSeatType()) {  

						case CLASSIC:
							price = movieShow.getClassicSeatPrice();
							booking.setSeatPrice(price);
							totalPrice += price;
							break;
						case GOLD:
							price = movieShow.getGoldSeatPrice();
							booking.setSeatPrice(price);
							totalPrice += price;
							break;
						case PLATINUM:
							price = movieShow.getPlatinumSeatPrice();
							booking.setSeatPrice(price);
							totalPrice += price;
							break;
						}

						// SET SEAT TO CUSTOMER
						if (listSeat.isEmpty())
							listSeat = new ArrayList<>();
						// ADD SEAT CUSTOMER SEATLIST
						listSeat.add(seat);
						// SET ABOVE LIST TO CUSTOMER
						customer.setSeats(listSeat);

						if (listBooking == null)
							listBooking = new ArrayList<>();
						// ADD BOOKING IN LIST
						listBooking.add(booking);

					} else
						throw new ShowHasCancelledOrClosed("Fail to book");
				} else
					throw new SeatAlreadyBooked("Fail to book this seat:" + id);
			} else
				throw new ShowIsHousefull("Fail to Book");
		} // LOOP
			
					// SET ALL DATA TO TICKET
					ticket.setTotalPrice(totalPrice);
					ticket.setTicketStatus(TicketStatus.ACTIVE);
					ticket.setShow(movieShow);
					ticket.setBookings(listBooking);
					ticket.setCustomer(customer);

		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.CREATED.value(), 
															"Ticket Book Successfully...",
															 ticketDao.saveTicket(ticket)), 
															 HttpStatus.CREATED);

	}

//	CANCEL
	public ResponseEntity<ResponseStructure<Ticket>> cancelTicket(long customerId, long ticketId) {

		Ticket ticket = ticketDao.findTicketById(ticketId);

		if (ticket.getCustomer().getCustomerId() == customerId) {

			switch (ticket.getShow().getShowStatus()) {
			case ONGOING:
				throw new ShowHasAlreadyStrated("Fail to cancel...");
			case CLOSED:
				throw new ShowAlreadyClosed("Fail to Cancel...");
			case CANCELLED:
				throw new ShowAlreadyCancelled("Fail to cancel...");
			case ACTIVE: {

				switch (ticket.getTicketStatus()) {
				case EXPIRED:
					throw new TicketAlreadyExpired("Fail to Cancel...");
				case CANCELLED:
					throw new TicketAlreadyCancelled("Fail to Cancel..");
				case ACTIVE: {
					List<Booking> booking = ticket.getBookings();
					for (Booking book : booking) {
						book.setBookingStatus(BookingStatus.CANCELLED);
						//change seat status booked to available
						seatDao.findSeatById(book.getSeatId()).setSeatStatus(SeatStatus.AVAILABLE);	
					}	
					    ticket.getCustomer().setSeats(null);
					    ticket.setTicketStatus(TicketStatus.CANCELLED);
					   

					return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
																		"Ticket Cancelled",
																		 ticketDao.saveTicket(ticket)), 
																		 HttpStatus.FOUND);
				}

				}
			}

			}
		}
		throw new UnauthorizedCustomer("Fail to Cancel....");
	}

	
	public ResponseEntity<ResponseStructure<Long>> getBookingSeat(long movieShowId) {
		
		Screen screen = screenDao.findScreenById(movieShowDao.findShowById(movieShowId).getScreenId());
	
		return new ResponseEntity<>(new ResponseStructure<>(HttpStatus.FOUND.value(),
															"Booking count : - ",
															 seatDao.countSeatBySeatStatus(SeatStatus.BOOKED, screen)), 
															 HttpStatus.FOUND);
	}
}// class