package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Portfolio;

import java.util.Objects;

@DynamoDBTable(tableName = "User")
public class UserRecord {

    private String userId;
    private String username;
    private String password;
    private Portfolio portfolio;

    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "Username")
    public String getUsername() {
        return username;
    }

    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return password;
    }

    @DynamoDBAttribute(attributeName = "Portfolio")
    public Portfolio getPortfolio() {
        return portfolio;
    }
    public void setId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRecord userRecord = (UserRecord) o;
        return Objects.equals(userId, userRecord.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
