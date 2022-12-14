package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.UserCreateRequest;
import com.kenzie.appserver.controller.model.UserResponse;
import com.kenzie.appserver.service.UserService;

import com.kenzie.appserver.service.model.Portfolio;
import com.kenzie.appserver.service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> get(@PathVariable("userId") String id) {

        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserId());
        userResponse.setUsername(user.getUsername());
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = new User(randomUUID().toString(), userCreateRequest.getUsername(),  new Portfolio());
        userService.addNewUser(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserId());
        userResponse.setUsername(user.getUsername());

        return ResponseEntity.created(URI.create("/user/" + userResponse.getId())).body(userResponse);
    }
}
