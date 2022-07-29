/**
 * 
 */
package com.example.airline.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FLIGHT")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Flight {
	@Id
	private String flightNumber;
	
	@Column(name = "ORIGIN")
	private String origin;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "DURATION")
	private Float duration;
}
