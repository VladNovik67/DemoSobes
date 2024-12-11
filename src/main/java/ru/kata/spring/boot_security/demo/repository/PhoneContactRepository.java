package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.PhoneContact;

public interface PhoneContactRepository extends JpaRepository<PhoneContact, Long> {
}
