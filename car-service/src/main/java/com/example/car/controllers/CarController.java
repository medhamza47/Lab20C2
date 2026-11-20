package com.example.car.controllers;

import com.example.car.models.CarResponse;
import com.example.car.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService service;

    @GetMapping
    public List<CarResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> findById(@PathVariable Long id) {
        return ResponseEntity.of(service.findById(id));
    }
}
