package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Client;

import java.util.Collection;

public interface ClientService {

    Collection<Client> findAll();

    void delete(Long id);

    void save(Client client);

    Client findById(Long id);

    Client findClientByName(String name);
}
