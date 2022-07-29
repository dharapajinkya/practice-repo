/**
 * 
 */
package com.example.airline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FLIGHT")
public class Flight {	

	@Id
	private String flightNumber;
	
	@Column(name = "ORIGIN")
	private String origin;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "DURATION")
	private Float duration;
	
	public Flight(String flightNumber, String origin, String destination, Float duration) {
		super();
		this.flightNumber = flightNumber;
		this.origin = origin;
		this.destination = destination;
		this.duration = duration;
	}
	
	
	
	public Flight() {
		
	}



	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}
}
