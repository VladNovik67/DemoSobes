package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Client;
import ru.kata.spring.boot_security.demo.model.ClientContactsDTO;
import ru.kata.spring.boot_security.demo.model.EmailContact;
import ru.kata.spring.boot_security.demo.model.PhoneContact;
import ru.kata.spring.boot_security.demo.repository.ClientRepository;
import ru.kata.spring.boot_security.demo.util.ClientNotFoundException;

import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(Client client) {
        repository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Client findClientByName(String name) {
        return repository.findByFirstName(name).orElse(null);
    }

    // Получение клиента с контактами
    public Client getClientWithContacts(Long id) {
        Client client = repository.findById(id).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException();
        } else {
            return client;
        }
    }

    // Получение всех телефонных контактов клиента
    public Set<PhoneContact> getPhoneContacts(Long id) {
        Client client = repository.findById(id).orElse(null);
        if (client == null) {
            throw new ClientNotFoundException();
        } else {
            return client.getPhones_contact();  // возвращаем все телефоны клиента

        }
    }

    // Получение всех email контактов клиента
    public Set<EmailContact> getEmailContacts(Long id) {
        Client client = repository.findById(id).orElse(null);
        if (!(client == null)) {
            return client.getEmails_contact();  // возвращаем все email-адреса клиента
        } else {
            throw new ClientNotFoundException();
        }
    }

    // Получение всех контактов клиента (телефоны и email)
    public ClientContactsDTO getClientContacts(Long id) {
        Client client = repository.findById(id).orElse(null);
        if (!(client == null)) {
            Set<PhoneContact> phoneContacts = client.getPhones_contact(); // Получаем телефоны
            Set<EmailContact> emailContacts = client.getEmails_contact(); // Получаем email-адреса

            return new ClientContactsDTO(phoneContacts, emailContacts); // Возвращаем DTO с контактами
        } else {
            throw new ClientNotFoundException();
        }
    }

}
