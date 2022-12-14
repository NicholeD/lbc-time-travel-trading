package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.ContactCreateRequest;
import com.kenzie.appserver.service.ContactService;
import com.kenzie.appserver.service.model.Contact;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class ContactControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ContactService contactService;

    private final MockNeat mockNeat = MockNeat.threadLocal();
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        contactService = mock(ContactService.class);
    }

    @Test
    public void getContact() throws Exception {
        //GIVEN
        String id = mockNeat.strings().get();
        String name = mockNeat.strings().get();
        String email = mockNeat.strings().get();
        String subject = mockNeat.strings().get();
        String message = mockNeat.strings().get();

        Contact contact = new Contact(id, name, email, subject, message);

        when(contactService.addNewContact(contact)).thenReturn(contact);

        Contact addedContact = contactService.addNewContact(contact);

        mvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addedContact)))
                .andExpect(status().is(201));

        //WHEN
        //This code will not work until the /contact endpoint is created
//        mvc.perform(get("/contact/{id}", id)
//                    .accept(MediaType.APPLICATION_JSON))
//        //THEN
//                .andExpect(jsonPath("id")
//                        .exists())
//                .andExpect(jsonPath("name")
//                        .exists())
//                .andExpect( jsonPath("email")
//                        .exists())
//                .andExpect(jsonPath("subject")
//                        .exists())
//                .andExpect( jsonPath("message")
//                        .exists())
//                .andExpect(status().isOk());
    }

    @Test
    public void getContact_notFound() throws Exception {
        //GIVEN
        String id = mockNeat.strings().get();

        when(contactService.findById(id)).thenReturn(null);

        //WHEN
        mvc.perform(get("/contact/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
        //THEN
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewContact() throws Exception {
        //GIVEN
        String id = "123456";
        String name = "name";
        String email = "email";
        String subject = "subject";
        String message = "message";
        Contact contact = new Contact(id, name, email, subject, message);

        ContactCreateRequest contactCreateRequest = new ContactCreateRequest();
        contactCreateRequest.setId(id);
        contactCreateRequest.setName(name);
        contactCreateRequest.setEmail(email);
        contactCreateRequest.setMessage(message);
        contactCreateRequest.setSubject(subject);

        mapper.registerModule(new JavaTimeModule());

        //WHEN
        mvc.perform(post("/contact", contactCreateRequest)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(contactCreateRequest)))
        //THEN
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .exists())
                .andExpect(jsonPath("email")
                        .exists())
                .andExpect(jsonPath("subject")
                        .exists())
                .andExpect(jsonPath("message")
                        .exists())
                .andExpect(status().isCreated());
    }
}
