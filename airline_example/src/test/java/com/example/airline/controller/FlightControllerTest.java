package com.example.airline.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.airline.model.Flight;
import com.example.airline.repository.FlightRepository;
import com.example.airline.service.FlightService;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

	@MockBean
	private FlightService flightService;
	
	@MockBean FlightRepository flightRepository;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testGetFlights() throws Exception {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		Flight flight1 = new Flight("ADD121", "PUN", "KOL", 4800f);
		List<Flight> flights = Stream.of(flight, flight1).collect(Collectors.toList());
		Mockito.when(flightService.getAllFlights()).thenReturn(flights);
		mockMvc.perform(get("/api/flights")).andExpect(status().isOk());
	}

	@Test
	void testGetFlight() throws Exception {
		String flightNumber = "ADD111";
		Flight flight = new Flight(flightNumber, "PUN", "MUM", 3600f);
		Mockito.when(flightService.getFlight(flightNumber)).thenReturn(flight);
		mockMvc.perform(get("/api/flights/{flightNumber}", flightNumber)).andExpect(status().isOk());
		assertEquals(flight.getFlightNumber(), flightNumber);
	}

	@Test
	void testSaveFlight() throws Exception {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		doNothing().when(flightRepository.save(flight));
		mockMvc.perform(post("/api/flights/",flight)).andExpect(status().isCreated());
	}

	@Test
	void testDeleteFlight() {
		
	}

	@Test
	void testUpdateFlight() {
		
	}

}
