package com.jsp.CloneAPIBookMyShow.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jsp.CloneAPIBookMyShow.enums.TicketStatus;

import lombok.Data;

@Entity
@Data
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ticketId;
	private double totalPrice;
	
	//unidirectional
	private TicketStatus ticketStatus;
	@ManyToOne
	private MovieShow show;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Booking> bookings;
	@ManyToOne
	private Customer customer;
}