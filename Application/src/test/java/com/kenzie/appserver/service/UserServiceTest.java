package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }
    /** ------------------------------------------------------------------------
     *  userService.findById
     *  ------------------------------------------------------------------------ **/

    @Test
    void findById() {
        // GIVEN
        String id = randomUUID().toString();

        UserRecord record = new UserRecord();
        record.setId(id);
        record.setUsername("username");

        // WHEN
        when(userRepository.findById(id)).thenReturn(Optional.of(record));
        User user = userService.findById(id);

        // THEN
        Assertions.assertNotNull(user, "The object is returned");
        Assertions.assertEquals(record.getUserId(), user.getUserId(), "The id matches");
        Assertions.assertEquals(record.getUsername(), user.getUsername(), "The name matches");
    }

    @Test
    void findByConcertId_invalid() {
        // GIVEN
        String id = randomUUID().toString();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        User user = userService.findById(id);

        // THEN
        Assertions.assertNull(user, "The example is null when not found");
    }

}
