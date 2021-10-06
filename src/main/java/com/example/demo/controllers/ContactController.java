package com.example.demo.controllers;

import com.example.demo.models.Contact;
import com.example.demo.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public ResponseEntity<List<Contact>> getAllContacts(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        try {
            List<Contact> contacts = new ArrayList<>();
            if (firstName == null && lastName == null) {
                contactRepository.findAll().forEach(contacts::add);
            } else if (firstName != null && !firstName.isEmpty()) {
                contactRepository.findByFirstName(firstName).forEach(contacts::add);
            } else if (lastName != null && !lastName.isEmpty()) {
                contactRepository.findByLastName(lastName).forEach(contacts::add);
            } else {
                contactRepository.findByFirstNameAndLastName(firstName, lastName).forEach(contacts::add);
            }

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable("id") long id) {
        Optional<Contact> contactData = contactRepository.findById(id);

        if (contactData.isPresent()) {
            return new ResponseEntity<>(contactData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<?> createContact(@Valid @RequestBody Contact contact, BindingResult bindingResult) {

        String error = checkBindingResult((bindingResult));
        if (error != null) {
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        try {
            Contact createdContact = contactRepository
                    .save(new Contact(contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhoneNumber(), contact.getPostalAddress()));
            return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") long id, @RequestBody Contact contact) {
        Optional<Contact> contactData = contactRepository.findById(id);

        if (contactData.isPresent()) {
            Contact updatedContact = contactData.get();
            if (StringUtils.hasLength(contact.getFirstName())) {
                updatedContact.setFirstName(contact.getFirstName());
            }
            if (StringUtils.hasLength(contact.getLastName())) {
                updatedContact.setLastName(contact.getLastName());
            }
            if (StringUtils.hasLength(contact.getEmail())) {
                updatedContact.setEmail(contact.getEmail());
            }
            if (StringUtils.hasLength(contact.getPhoneNumber())) {
                updatedContact.setPhoneNumber(contact.getPhoneNumber());
            }
            if (contact.getPostalAddress() != null) {
                updatedContact.setPostalAddress(contact.getPostalAddress());
            }
            return new ResponseEntity<>(contactRepository.save(updatedContact), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String checkBindingResult(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError fieldError: bindingResult.getFieldErrors()){
                errorMsg.append(fieldError.getDefaultMessage());
            }

            return errorMsg.toString();
        }

        return null;
    }

}
