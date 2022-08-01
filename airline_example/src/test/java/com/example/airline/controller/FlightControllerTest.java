package com.example.airline.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.airline.model.Flight;
import com.example.airline.repository.FlightRepository;
import com.example.airline.service.FlightService;
import com.example.airline.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

	@InjectMocks
	private FlightService flightService;

	@MockBean
	FlightRepository flightRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

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
		String responseString = Constants.SUCCESS;
		Mockito.when(flightRepository.save(flight)).thenReturn(flight);
		Mockito.when(flightService.saveFlight(flight)).thenReturn(responseString);
		mockMvc.perform(post("/api/createFlight/", flight).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(flight))).andExpect(status().isCreated());
		assertEquals(Constants.SUCCESS, responseString);
	}

	@Test
	void testDeleteFlight() throws Exception {
		String flightNumber = "ADD111";
		String responseString = Constants.SUCCESS;
		flightRepository.deleteById(flightNumber);
		Mockito.when(flightService.deleteFlight(flightNumber)).thenReturn(responseString);
		mockMvc.perform(delete("/deleteFlight/{flightNumber}", flightNumber)).andExpect(status().isOk());
	}

	@Test
	void testUpdateFlight() throws JsonProcessingException, Exception {
		Flight flight = new Flight("ADD111", "PUN", "MUM", 3600f);
		Flight updatedFlight = new Flight("ADD111", "PUN", "MUM", 4800f);
		String flightNumber = "ADD111";
		Mockito.when(flightRepository.findById(flightNumber)).thenReturn(Optional.of(flight));
		Mockito.when(flightRepository.save(any(Flight.class))).thenReturn(updatedFlight);
		mockMvc.perform(put("/api/flights/{flightNumber}", flightNumber).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedFlight))).andExpect(status().isOk());
	}

	@Test
	void testGetFlightsByOriginDestination() throws Exception {
		String origin = "PUN";
		String destination = "MUM";
		List<Flight> sortedFlights = getSortedFlightsByDuration(origin, destination);
		Mockito.when(flightService.getFlightByOriginAndDestination(origin, destination)).thenReturn(sortedFlights);
		MvcResult result = mockMvc.perform(get("/api/flights/{origin}/{destination}", origin, destination)).andReturn();
		assertEquals(HttpStatus.OK, result.getResponse().getStatus());
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
