package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.Objects;

@DynamoDBTable(tableName = "Contact")
public class ContactRecord {
    private String id;
    private String name;
    private String email;
    private String subject;
    private String message;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DynamoDBAttribute(attributeName = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    @DynamoDBAttribute(attributeName = "Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactRecord)) return false;
        ContactRecord that = (ContactRecord) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getSubject(), that.getSubject()) && Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getSubject(), getMessage());
    }
}
