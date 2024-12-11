package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Client;
import ru.kata.spring.boot_security.demo.model.ClientContactsDTO;
import ru.kata.spring.boot_security.demo.model.EmailContact;
import ru.kata.spring.boot_security.demo.model.PhoneContact;
import ru.kata.spring.boot_security.demo.service.ClientServiceImpl;
import ru.kata.spring.boot_security.demo.util.ClientErrorResponse;
import ru.kata.spring.boot_security.demo.util.ClientNotCreatedException;
import ru.kata.spring.boot_security.demo.util.ClientNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/client")
public class RestApiClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public RestApiClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientService.findAll().stream().toList();
        return !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public Client getUser(@PathVariable("id") long id) {
        return clientService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody @Valid Client client,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ClientNotCreatedException(errorMsg.toString());
        }
        clientService.save(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Client> update(@RequestBody @Valid Client client,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ClientNotCreatedException(errorMsg.toString());
        }
        clientService.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


    @PutMapping("/{id}/addPhone")
    public ResponseEntity<Client> addPhone(@PathVariable("id") long id, @RequestBody @Valid PhoneContact phoneContact, BindingResult bindingResult) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ClientNotFoundException();
        }

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ClientNotCreatedException(errorMsg.toString());
        }

        client.addNewPhoneContactClient(phoneContact);
        clientService.save(client);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/{id}/addEmail")
    public ResponseEntity<Client> addEmail(@PathVariable("id") long id, @RequestBody @Valid EmailContact emailContact, BindingResult bindingResult) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ClientNotFoundException();
        }

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ClientNotCreatedException(errorMsg.toString());
        }

        client.addNewEmailContactClient(emailContact);
        clientService.save(client);

        return new ResponseEntity<>(client, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        Client client = clientService.findById(id);
        if (client == null) {
            throw new ClientNotFoundException();
        }
        clientService.delete(id);
        return "User with ID = " + id + " was deleted";
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(ClientNotFoundException e) {
        ClientErrorResponse response = new ClientErrorResponse(
                "Person with this id wash't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handleException(ClientNotCreatedException e) {
        ClientErrorResponse response = new ClientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Получение всех контактов клиента (телефоны и email)
    @GetMapping("/{clientId}/contacts")
    public ResponseEntity<ClientContactsDTO> getClientContacts(@PathVariable Long clientId) {
        ClientContactsDTO clientContacts = clientService.getClientContacts(clientId);
        return ResponseEntity.ok(clientContacts);
    }
}
