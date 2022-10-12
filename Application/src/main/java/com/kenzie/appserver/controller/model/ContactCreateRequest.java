package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

public class ContactCreateRequest {
    @NotEmpty
    @JsonProperty("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotEmpty
    @JsonProperty("email")
    private String email;

    public String getEmail(){
        return email;
    }

    @NotEmpty
    @JsonProperty("subject")
    private String subject;

    public String getSubject(){
        return subject;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }
    @NotEmpty
    @JsonProperty("message")
    private String message;

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

}
