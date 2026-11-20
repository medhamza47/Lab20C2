package com.example.client.services;

import com.example.client.entities.Client;
import com.example.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;

    public List<Client> findAll() {
        return repository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return repository.findById(id);
    }

    public Client addClient(Client client) {
        return repository.save(new Client(null, client.getNom(), client.getAge()));
    }
}
