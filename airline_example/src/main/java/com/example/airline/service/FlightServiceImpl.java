package com.example.airline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.airline.model.Flight;
import com.example.airline.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {
	@Autowired
	private FlightRepository flightRepository;

	public void setFlightRepository(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public List<Flight> getAllFlights() {
		List<Flight> flights = flightRepository.findAll();
		return flights;
	}

	public Flight getFlight(String flightNumber) {
		Optional<Flight> optFlight = flightRepository.findById(flightNumber);
		return optFlight.get();
	}

	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
	}

	public void deleteFlight(String flightNumber) {
		flightRepository.deleteById(flightNumber);
	}

	public void updateFlight(Flight flight) {
		flightRepository.save(flight);
	}
}
