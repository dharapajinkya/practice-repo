package com.example.airline.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

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
		Flight returnedFlight = flight;
		flightService.saveFlight(flight);
		Assertions.assertNotNull(returnedFlight);		
	}

	@Test
	void testDeleteFlight() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateFlight() {
		fail("Not yet implemented");
	}

}
