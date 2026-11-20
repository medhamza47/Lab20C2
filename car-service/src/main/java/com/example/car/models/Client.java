package com.example.car.models;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Client(Long id, @JsonAlias("nom") String name, Float age) {
}
