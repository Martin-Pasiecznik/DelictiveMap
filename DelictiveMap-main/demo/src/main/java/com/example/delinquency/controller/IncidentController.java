package com.example.delinquency.controller;

import com.example.delinquency.model.Incident;
import com.example.delinquency.repository.IncidentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incidents") // Ruta base para el controlador
public class IncidentController {

    private final IncidentRepository incidentRepository;

    public IncidentController(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }


    // Crear un nuevo incidente (POST)
    @PostMapping
    public ResponseEntity<Incident> createIncident(@RequestBody Incident incident) {
        try {
            Incident savedIncident = incidentRepository.save(incident);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedIncident);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Obtener todos los incidentes (GET)
    @GetMapping
    public ResponseEntity<List<Incident>> getAllIncidents() {
        List<Incident> incidents = incidentRepository.findAll();
        return ResponseEntity.ok(incidents);
    }

    // Obtener un incidente por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        return incident.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un incidente por ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Incident> updateIncident(@PathVariable Long id, @RequestBody Incident updatedIncident) {
        Optional<Incident> existingIncident = incidentRepository.findById(id);
        if (existingIncident.isPresent()) {
            Incident incident = existingIncident.get();
            incident.setType(updatedIncident.getType());
            incident.setDescription(updatedIncident.getDescription());
            incident.setLatitude(updatedIncident.getLatitude());
            incident.setLongitude(updatedIncident.getLongitude());
            incident.setDate(updatedIncident.getDate());
            Incident savedIncident = incidentRepository.save(incident);
            return ResponseEntity.ok(savedIncident);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Eliminar un incidente por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        Optional<Incident> existingIncident = incidentRepository.findById(id);
        if (existingIncident.isPresent()) {
            incidentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}