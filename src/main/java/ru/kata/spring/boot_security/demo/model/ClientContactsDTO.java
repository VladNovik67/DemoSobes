package ru.kata.spring.boot_security.demo.model;

import java.util.Set;

public class ClientContactsDTO {
    private Set<PhoneContact> phoneContacts;
    private Set<EmailContact> emailContacts;

    // Конструктор
    public ClientContactsDTO(Set<PhoneContact> phoneContacts, Set<EmailContact> emailContacts) {
        this.phoneContacts = phoneContacts;
        this.emailContacts = emailContacts;
    }

    // Геттеры и сеттеры
    public Set<PhoneContact> getPhoneContacts() {
        return phoneContacts;
    }

    public void setPhoneContacts(Set<PhoneContact> phoneContacts) {
        this.phoneContacts = phoneContacts;
    }

    public Set<EmailContact> getEmailContacts() {
        return emailContacts;
    }

    public void setEmailContacts(Set<EmailContact> emailContacts) {
        this.emailContacts = emailContacts;
    }
}
