package com.example.car.services;

import com.example.car.entities.Car;
import com.example.car.models.CarResponse;
import com.example.car.models.Client;
import com.example.car.repositories.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarService.class);

    private final CarRepository repository;
    private final RestTemplate restTemplate;
    private final String clientUrl;

    public CarService(CarRepository repository, RestTemplate restTemplate,
                      @Value("${lab.client-url}") String clientUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.clientUrl = clientUrl;
    }

    public List<CarResponse> findAll() {
        return repository.findAll().stream().map(this::mapToCarResponse).toList();
    }

    public Optional<CarResponse> findById(Long id) {
        return repository.findById(id).map(this::mapToCarResponse);
    }

    private CarResponse mapToCarResponse(Car car) {
        Client client = null;
        if (car.getClientId() != null) {
            try {
                client = restTemplate.getForObject(clientUrl + "/{id}", Client.class, car.getClientId());
            } catch (RestClientException exception) {
                LOGGER.warn("Client {} indisponible pour la voiture {} : {}",
                        car.getClientId(), car.getId(), exception.getMessage());
            }
        }

        return CarResponse.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .matricule(car.getMatricule())
                .client(client)
                .build();
    }
}
