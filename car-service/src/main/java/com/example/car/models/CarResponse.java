package com.example.car.models;

import lombok.Builder;

@Builder
public record CarResponse(Long id, String brand, String model, String matricule, Client client) {
}
