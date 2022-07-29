package com.example.airline.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

	@Override
	public List<Flight> getFlightByOriginAndDestination(String origin, String destination) {
		List<Flight> flights = flightRepository.findAll();
		Predicate<Flight> filterByOrigin = str -> str.getOrigin().equals(origin);
		Predicate<Flight> filterByDest = str -> str.getDestination().equals(destination);
		List<Flight> sortedFlights = flights.stream().filter(filterByOrigin.and(filterByDest))
				.sorted(Comparator.comparingDouble(Flight::getDuration)).collect(Collectors.toList());
		return sortedFlights;
	}
}
