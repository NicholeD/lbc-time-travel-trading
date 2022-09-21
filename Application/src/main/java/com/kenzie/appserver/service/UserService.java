package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.service.model.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(String id) {
        User userFromBackend = userRepository
                .findById(id)
                .map(user -> new User(user.getUserId(), user.getUsername(), user.getPassword(), user.getPortfolio()))
                .orElse(null);

        return userFromBackend;
    }

    public User addNewUser(User user) {
        UserRecord userRecord = new UserRecord();
        userRecord.setId(user.getUserId());
        userRecord.setUsername(user.getUsername());
        userRecord.setPassword(user.getPassword());
        userRecord.setPortfolio(user.getPortfolio());
        userRepository.save(userRecord);
        return user;
    }
}
