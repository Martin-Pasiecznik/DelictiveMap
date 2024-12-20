package com.example.delinquency.controller;

import com.example.delinquency.model.Incident;
import com.example.delinquency.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @PostMapping("/incident")
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        Incident savedIncident = incidentRepository.save(incident);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedIncident);
    }
    
    @GetMapping("/incident/test")
    public ResponseEntity<String> testIncidentEndpoint() {
        return ResponseEntity.ok("Endpoint funcionando");
    }
    
}