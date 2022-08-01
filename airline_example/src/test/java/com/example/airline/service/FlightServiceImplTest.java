package com.example.airline.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.airline.model.Flight;
import com.example.airline.repository.FlightRepository;
import com.example.airline.util.Constants;

@SpringBootTest
class FlightServiceImplTest {
	
	@Autowired
	private FlightService flightService;
	
	@MockBean
	private FlightRepository flightRepository;

	@Test
	void testGetAllFlights() {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		Flight flight1 = new Flight("ADD121", "PUN", "KOL", 4800f);
		List<Flight> flights = Stream.of(flight, flight1).collect(Collectors.toList());
		doReturn(flights).when(flightRepository).findAll();
		List<Flight> returnedFlights = flightService.getAllFlights();
		Assertions.assertEquals(2, returnedFlights.size());
	}

	@Test
	void testGetFlight() {
		String flightNumber = "ADD111";
		Flight flight = new Flight(flightNumber, "PUN", "MUM", 3600f);
		doReturn(Optional.of(flight)).when(flightRepository).findById(flightNumber);
		Optional<Flight> returnedFlight = Optional.of(flightService.getFlight(flightNumber));
		Assertions.assertTrue(returnedFlight.isPresent());
		Assertions.assertSame(returnedFlight.get(), flight);
	}

	@Test
	void testSaveFlight() {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		doReturn(flight).when(flightRepository).save(any());
		String resString = flightService.saveFlight(flight);
		Assertions.assertEquals(Constants.SUCCESS,resString);		
	}

	@Test
	void testDeleteFlight() {
		String flightNumber = "ADD111";
		doNothing().when(flightRepository).deleteById(flightNumber);
		String resString = flightService.deleteFlight(flightNumber);
		Assertions.assertEquals(Constants.SUCCESS,resString);
	}

	@Test
	void testUpdateFlight() {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		Flight upFlight = new Flight("ADD111", "PUN", "MUM", 4800f);
		doReturn(upFlight).when(flightRepository).save(any());
		String resString = flightService.saveFlight(upFlight);
		Assertions.assertEquals(flight.getFlightNumber(), upFlight.getFlightNumber());
		Assertions.assertEquals(Constants.SUCCESS,resString);
	}
	
	@Test
	void testGetFlightByOriginAndDestination() {
		String origin = "PUN";
		String destination = "MUM";
		List<Flight> flights = getAllFlights();
		doReturn(flights).when(flightRepository).findAll();
		List<Flight> sortedFlights = getSortedFlightsByDuration(origin, destination);
		List<Flight> flightsForODPair = flightService.getFlightByOriginAndDestination(origin, destination);		
		Assertions.assertEquals(sortedFlights.size(), flightsForODPair.size());
	}
	
	private List<Flight> getAllFlights(){
		Flight f1 = new Flight("ADD111", "PUN", "MUM", 3600f);
		Flight f2 = new Flight("ADD121", "PUN", "MUM", 4600f);
		Flight f3 = new Flight("ADD131", "PUN", "MUM", 600f);
		Flight f4 = new Flight("ADD141", "PUN", "MUM", 300f);
		Flight f5 = new Flight("ADD151", "PUN", "MUM", 13600f);
		Flight f6 = new Flight("ADD161", "PUN", "MUM", 30600f);
		Flight f7 = new Flight("ADD171", "PUN", "MUM", 4800f);
		Flight f8 = new Flight("ADD181", "PUN", "KOL", 4800f);
		Flight f9 = new Flight("ADD191", "PUN", "DEL", 4800f);
		List<Flight> flights = Stream.of(f1, f2, f3, f4, f5, f6, f7,f8,f9).collect(Collectors.toList());
		return flights;
	}
	
	private List<Flight> getSortedFlightsByDuration(String origin, String destination) {
		Flight f1 = new Flight("ADD111", origin, destination, 3600f);
		Flight f2 = new Flight("ADD121", origin, destination, 4600f);
		Flight f3 = new Flight("ADD131", origin, destination, 600f);
		Flight f4 = new Flight("ADD141", origin, destination, 300f);
		Flight f5 = new Flight("ADD151", origin, destination, 13600f);
		Flight f6 = new Flight("ADD161", origin, destination, 30600f);
		Flight f7 = new Flight("ADD171", origin, destination, 4800f);
		List<Flight> flights = Stream.of(f1, f2, f3, f4, f5, f6, f7).collect(Collectors.toList());
		List<Flight> sortedFlights = flights.stream().sorted(Comparator.comparingDouble(Flight::getDuration))
				.collect(Collectors.toList());
		return sortedFlights;
	}

}
