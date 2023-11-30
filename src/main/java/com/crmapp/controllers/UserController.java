package com.crmapp.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crmapp.dtos.UserCreateDTO;
import com.crmapp.models.User;
import com.crmapp.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Object> users(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "first_name", required = false) String first_name,
            @RequestParam(name = "last_name", required = false) String last_name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size); // subtract 1 from page

        return this.userService.getUsers(query, first_name, last_name, email, phone,
                address, pageable);
    }

    @PostMapping()
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = new User();

        user.setFirst_name(userCreateDTO.getFirst_name());
        user.setLast_name(userCreateDTO.getLast_name());
        user.setEmail(userCreateDTO.getEmail());
        user.setPhone(userCreateDTO.getPhone());
        user.setAddress(userCreateDTO.getAddress());

        return this.userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserCreateDTO userCreateDTO,
            @PathVariable("id") UUID id) {
        User user = new User();

        user.setFirst_name(userCreateDTO.getFirst_name());
        user.setLast_name(userCreateDTO.getLast_name());
        user.setEmail(userCreateDTO.getEmail());
        user.setPhone(userCreateDTO.getPhone());
        user.setAddress(userCreateDTO.getAddress());

        return this.userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") UUID id) {
        return this.userService.deleteUser(id);
    }
}
