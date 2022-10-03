package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.ContactRepository;
import com.kenzie.appserver.repositories.model.ContactRecord;
import com.kenzie.appserver.service.model.Contact;
import com.kenzie.appserver.service.model.Example;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact findById(String id){
        Contact contactFromBackend = contactRepository
                .findById(id)
                .map(contact -> new Contact(contact.getId(), contact.getName(),contact.getEmail(),contact.getSubject(),contact.getMessage()))
                .orElse(null);
        return contactFromBackend;
    }

    public Contact addNewContact(Contact contact){
        ContactRecord contactRecord = new  ContactRecord();
        contactRecord.setId(contact.getId());
        contactRecord.setName(contact.getName());
        contactRecord.setEmail(contact.getEmail());
        contactRecord.setSubject(contact.getSubject());
        contactRecord.setMessage(contact.getMessage());
        contactRepository.save(contactRecord);
        return contact;
    }
}
