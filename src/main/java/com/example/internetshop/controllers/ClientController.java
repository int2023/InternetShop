package com.example.internetshop.controllers;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Client;
import com.example.internetshop.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public ResponseEntity<?> addClient (@RequestBody Client client) {
        int inn = client.getClientINN();
        Optional<Client> byINN = clientRepository.findById(inn);
        if (byINN.isPresent()) {
            return new ResponseEntity<>
            ("Client is already exist", HttpStatus.BAD_REQUEST);
        }
        clientRepository.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllClients () {
        List<Client> clients = new ArrayList<>();
        for (Client client : clientRepository.findAll()) {
            clients.add(client);
        }
        return new ResponseEntity<>(clients,HttpStatus.OK);
    }

    @GetMapping("/{clientINN}")
    public ResponseEntity<?> getClientByINN (@PathVariable int clientINN) {
        if (clientRepository.findById(clientINN).isEmpty()) {
            return new ResponseEntity<>
            ("Client with such INN doesn't exist",HttpStatus.NOT_FOUND);
        }
        Client requestedClient = clientRepository.findById(clientINN).get();
        return new ResponseEntity<>(requestedClient,HttpStatus.OK);
    }

    @DeleteMapping("/{clientINN}")
    public ResponseEntity<?> deleteClientByINN (@PathVariable int clientINN) {
        if (clientRepository.findById(clientINN).isEmpty()) {
            return new ResponseEntity<>
            ("Client with such INN doesn't exist",HttpStatus.NOT_FOUND);
        }
        clientRepository.delete(clientRepository.findById(clientINN).get());
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }

}
