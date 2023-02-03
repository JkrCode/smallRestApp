package com.divae_happyBirthday.myfirstrestapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    private ResponseEntity<User> register (@RequestBody User newUser){
        //Generate API Key
        newUser.setSecret(UUID.randomUUID().toString());

        var savedUser = userRepository.save(newUser);
        return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    private ResponseEntity<User> getuser (@RequestParam(name= "id") int id){
        Optional<User> requestedUser = userRepository.findById(id);

        if(requestedUser.isPresent()){
            return new ResponseEntity<User> (requestedUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity("no user found with the id: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/validate")
    private ResponseEntity<String> validate (@RequestParam(name= "email") String email,
                                           @RequestParam(name= "password") String password) {
        var validUser = userRepository.findByEmailAndPassword(email, password);
        if (validUser.isPresent()){
            return new ResponseEntity<String>("API Secret: "+validUser.get().getSecret(), HttpStatus.OK);
        }
        return new ResponseEntity("Wrong credentials",HttpStatus.NOT_FOUND);
    }
}
