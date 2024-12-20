package com.example.delinquency.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.delinquency.model.Incident;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
	
}