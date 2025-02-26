package com.contactmanagement.controller;

import com.contactmanagement.model.User;
import com.contactmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Add a new user
    @PostMapping("/addUsers")
    public String addUser(@RequestBody User user) {
        userRepository.save(user);
        return "User added successfully!";
    }

    // View all users
    @GetMapping("/viewAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user by phone number
    @PostMapping("/updateUser")
    public String updateUser(@RequestParam String phoneNumber, @RequestBody User updatedUser) {
        Optional<User> userOpt = userRepository.findByPhoneNumber(phoneNumber);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            userRepository.save(user);
            return "User updated successfully!";
        } else {
            return "User not found!";
        }
    }

    // Delete user by email
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return "User deleted successfully!";
        } else {
            return "User not found!";
        }
    }
}
