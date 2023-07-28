package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    Collection<User> findAll();

    void delete(Long id);

    void save(User user);

    User findById(Long id);

    User findByEmail(String email);
}
