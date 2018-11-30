package org.csobik.example.compose.controller;

import lombok.AllArgsConstructor;
import org.csobik.example.compose.entity.User;
import org.csobik.example.compose.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name, @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}