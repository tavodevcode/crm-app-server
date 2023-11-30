package com.crmapp.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crmapp.models.Post;
import com.crmapp.models.User;
import com.crmapp.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String ERROR = "error";

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> getUsers(String query, String first_name, String last_name, String email,
            String phone, String address, Pageable pageable) {
        HashMap<String, Object> res = new HashMap<>();
        HashMap<String, Object> meta = new HashMap<>();
        HashMap<String, Object> pagination = new HashMap<>();

        try {
            Page<User> users = null;

            if (query != null) {
                users = this.userRepository.findByQuery(query, pageable);
            } else {
                users = this.userRepository.findByContaining(first_name, last_name, email, phone, address,
                        pageable);
            }

            res.put(DATA, users.getContent());

            pagination.put("page_number", pageable.getPageNumber() + 1);
            pagination.put("page_size", pageable.getPageSize());
            pagination.put("total_pages", users.getTotalPages());
            pagination.put("total", users.getTotalElements());

            meta.put("pagination", pagination);
            res.put("meta", meta);
            res.put(DATA, users.getContent());

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Error: " + e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createUser(User user) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            User newUser = this.userRepository.save(user);

            res.put(DATA, newUser);

            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            res.put(ERROR, true);
            res.put(MESSAGE, "Error: " + e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateUser(User user, UUID id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            Optional<User> userExist = this.userRepository.findById(id);

            if (userExist.isEmpty()) {
                res.put(ERROR, true);
                res.put(MESSAGE, "User not found");

                return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            }

            User existingUser = userExist.get();

            existingUser.setFirst_name(user.getFirst_name());
            existingUser.setLast_name(user.getLast_name());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setAddress(user.getAddress());

            res.put(DATA, this.userRepository.save(existingUser));

            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            res.put(ERROR, "Error: " + e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteUser(UUID id) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            Optional<User> userExist = this.userRepository.findById(id);

            if (userExist.isEmpty()) {
                res.put(ERROR, true);
                res.put(MESSAGE, "User not found");
                return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
            }

            this.userRepository.deleteById(id);

            res.put(DATA, "User deleted");

            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            res.put(ERROR, "Error: " + e.getMessage());
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
