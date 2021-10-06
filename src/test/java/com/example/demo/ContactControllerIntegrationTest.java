package com.example.demo;


import com.example.demo.models.Contact;
import com.example.demo.repositories.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ContactControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ContactRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateContact() throws IOException, Exception {
        Contact newContact = new Contact("John", "Doe", "john@john", "0909090909", 70000);
        mvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(newContact)));

        List<Contact> found = repository.findAll();
        assertThat(found).extracting(Contact::getFirstName).containsOnly("John");
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenStatus200() throws Exception {
        createTestContact("John", "Doe", "john@john", "0909090909");
        createTestContact("David", "Lang", "david@david", "0908080808");

        mvc.perform(get("/contact").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].lastName", is("Lang")));
    }

    private void createTestContact(String firstName, String lastName, String email, String phoneNumber) {
        Contact contact = new Contact(firstName, lastName, email, phoneNumber, 700000);
        repository.saveAndFlush(contact);
    }

}
