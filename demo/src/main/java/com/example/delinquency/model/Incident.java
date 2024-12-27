package com.example.delinquency.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String description;
    private Double latitude;
    private Double longitude;
    private LocalDate date;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitud) {
		this.latitude = latitud;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitud) {
		this.longitude = longitud;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	
}