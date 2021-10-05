package com.example.demo.repositories;

import com.example.demo.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByFirstName(String firstName);

    List<Contact> findByLastName(String lastName);

    List<Contact> findByFirstNameAndLastName(String firstName, String lastName);
}
