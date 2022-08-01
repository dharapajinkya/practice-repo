package com.example.airline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.airline.model.Flight;
import com.example.airline.service.FlightService;
import com.example.airline.util.Constants;

@RestController
@RequestMapping("/api")
public class FlightController {

	@Autowired
	private FlightService flightService;

	public void setFlightService(FlightService flightService) {
		this.flightService = flightService;
	}

	@GetMapping("/allFlights")
	public ResponseEntity<List<Flight>> getFlights() {
		try {
			List<Flight> flights = flightService.getAllFlights();
			if (!flights.isEmpty()) {
				return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/flights/{flightNumber}")
	public ResponseEntity<Flight> getFlight(@PathVariable(name = "flightNumber", required = true) String flightNumber) {
		try {
			Flight flight = flightService.getFlight(flightNumber);
			if (flight == null) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(flight, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/flightsByOriginAndDestination/{origin}/{destination}")
	public ResponseEntity<List<Flight>> getFlightsByOriginDestination(
			@PathVariable(name = "origin", required = true) String origin,
			@PathVariable(name = "destination", required = true) String destination) {
		try {
			List<Flight> flights = flightService.getFlightByOriginAndDestination(origin, destination);
			if (flights.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<List<Flight>>(flights, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/createFlight")
	public ResponseEntity<HttpStatus> saveFlight(@RequestBody Flight flight) {
		try {
			String responseString = flightService.saveFlight(flight);
			System.out.println("Flight Saved Successfully");
			return Constants.SUCCESS.equals(responseString) ? new ResponseEntity<>(HttpStatus.CREATED)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteFlight/{flightNumber}")
	public ResponseEntity<HttpStatus> deleteFlight(@PathVariable(name = "flightNumber") String flightNumber) {
		try {
			String responseString = flightService.deleteFlight(flightNumber);
			System.out.println("Flight Deleted Successfully");
			return Constants.SUCCESS.equals(responseString) ? new ResponseEntity<>(HttpStatus.OK)
					: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateFlight/{flightNumber}")
	public ResponseEntity<Flight> updateFlight(@RequestBody Flight flight,
			@PathVariable(name = "flightNumber") String flightNumber) {
		try {
			Flight fl = flightService.getFlight(flightNumber);
			if (fl != null) {
				String responseString = flightService.updateFlight(flight);
				return Constants.SUCCESS.equals(responseString) ? new ResponseEntity<>(flight, HttpStatus.OK)
						: new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
