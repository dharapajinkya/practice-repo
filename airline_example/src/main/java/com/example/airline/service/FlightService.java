package com.example.airline.service;

import java.util.List;

import com.example.airline.model.Flight;

public interface FlightService {
	public List<Flight> getAllFlights();

	public Flight getFlight(String flightNumber);

	public void saveFlight(Flight flight);

	public void deleteFlight(String flightNumber);

	public void updateFlight(Flight flight);
	
	public List<Flight> getFlightByOriginAndDestination(String origin, String destination);
}
