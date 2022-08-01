package com.example.airline.service;

import java.util.List;

import com.example.airline.model.Flight;

public interface FlightService {
	public List<Flight> getAllFlights();

	public Flight getFlight(String flightNumber);

	public String saveFlight(Flight flight);

	public String deleteFlight(String flightNumber);

	public String updateFlight(Flight flight);
	
	public List<Flight> getFlightByOriginAndDestination(String origin, String destination);
}
