package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer postalAddress;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String email, String phoneNumber, Integer postalAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.postalAddress = postalAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(Integer postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + firstName + " " + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", postalAddress=" + postalAddress + "]";
    }
}
