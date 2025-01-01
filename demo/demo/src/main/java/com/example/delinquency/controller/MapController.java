package com.example.delinquency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/")
    public String index() {
        return "index"; // Retorna el nombre del archivo HTML (sin extensión)
    }
}