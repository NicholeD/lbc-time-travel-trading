package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ContactCreateRequest;
import com.kenzie.appserver.controller.model.ContactResponse;
import com.kenzie.appserver.service.ContactService;
import com.kenzie.appserver.service.model.Contact;
import com.kenzie.appserver.service.model.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @GetMapping("/id")
    public ResponseEntity<ContactResponse> get(@PathVariable("id") String id){
        Contact contact = contactService.findById(id);
        if(contact == null)
            return ResponseEntity.notFound().build();

        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setName(contact.getName());
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setSubject(contact.getSubject());
        contactResponse.setMessage(contact.getMessage());

        return ResponseEntity.ok(contactResponse);
    }

    @PostMapping
    public ResponseEntity<ContactResponse> addNewContact(@RequestBody ContactCreateRequest contactCreateRequest){
        Contact contact = new Contact(randomUUID().toString(), contactCreateRequest.getName(),contactCreateRequest.getEmail(),"Subject","Message");
        contactService.addNewContact(contact);

        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setId(contact.getId());
        contactResponse.setName(contact.getName());
        contactResponse.setEmail(contact.getEmail());
        contactResponse.setSubject(contact.getSubject());
        contactResponse.setMessage(contact.getMessage());

        return ResponseEntity.created(URI.create("/contact/"+ contactResponse.getId())).body(contactResponse);
    }

}
