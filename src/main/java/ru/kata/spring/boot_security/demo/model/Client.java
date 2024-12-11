package ru.kata.spring.boot_security.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name = "Name")
    private String firstName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_phone_contact",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_contact_id")
    )
    private Set<PhoneContact> phones_contact;

    @Email
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "client_email_contact",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "email_contact_id")
    )
    private Set<EmailContact> emails_contact;


    public void addNewPhoneContactClient(PhoneContact newPhoneContact) {
        phones_contact.add(newPhoneContact);
    }

    public void addNewEmailContactClient(EmailContact newEmailContact) {
        emails_contact.add(newEmailContact);
    }
}
