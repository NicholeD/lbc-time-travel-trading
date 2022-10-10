package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ContactRepository;
import com.kenzie.appserver.repositories.model.ContactRecord;
import com.kenzie.appserver.service.model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ContactServiceTest {
    private ContactRepository contactRepository;
    private ContactService contactService;

    @BeforeEach
    void setup() {
        this.contactRepository = mock(ContactRepository.class);
        this.contactService = new ContactService(contactRepository);
    }

    @Test
    void findById() {
        //GIVEN
        String id = randomUUID().toString();

        ContactRecord record = new ContactRecord();
        record.setId(id);
        record.setName("name");

        when(contactRepository.findById(any())).thenReturn(Optional.of(record));

        //WHEN
        Contact contact = contactService.findById(id);

        //THEN
        Assertions.assertNotNull(contact);
        Assertions.assertEquals(record.getId(), contact.getId());
        Assertions.assertEquals(record.getName(), contact.getName());
    }

    @Test
    void findById_invalid() {
        //GIVEN
        String id = randomUUID().toString();

        when(contactRepository.findById(any())).thenReturn(Optional.empty());

        //WHEN
        Contact contact = contactService.findById(id);

        //THEN
        Assertions.assertNull(contact);
    }

    @Test
    void addNewContact() {
        //GIVEN
        Contact contact = new Contact(randomUUID().toString(),
                "name",
                "name@name.com",
                "hello",
                "world");

        ArgumentCaptor<ContactRecord> contactRecordCaptor = ArgumentCaptor.forClass(ContactRecord.class);

        //WHEN
        Contact addedContact = contactService.addNewContact(contact);


        //THEN
        Assertions.assertNotNull(addedContact);
        verify(contactRepository).save(contactRecordCaptor.capture());

        Assertions.assertEquals(contact.getId(), addedContact.getId());
        Assertions.assertEquals(contact.getName(), addedContact.getName());
        Assertions.assertEquals(contact.getName(), addedContact.getName());


    }
}
