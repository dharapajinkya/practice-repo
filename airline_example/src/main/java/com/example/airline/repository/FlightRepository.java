package com.example.airline.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.airline.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

}
