package com.ceydog.mobileactionbootcamp.controller;


import com.ceydog.mobileactionbootcamp.entity.User;
import com.ceydog.mobileactionbootcamp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor //for userRepository
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public Object addNewUser(@Valid @RequestBody User user){

        //Since the id is auto generated, we don't have to check for it.
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public Object getAllUsers(){

        List<User> userList = userRepository.findAll();

        if (userList.isEmpty()) {
            throw new RuntimeException("Cannot access users, or there are no users.");
        }

        return ResponseEntity.ok(userList);
    }

    @GetMapping({"/{id}"})
    public Object getById(@PathVariable Long id){

        //Catch NoSuchElementException
        try {
            User foundUser = userRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(foundUser, HttpStatus.CREATED);
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such user exists.");
        }

    }

    @PutMapping
    public Object updateUser(@RequestBody User user){

        Long userIdParam = user.getUserId(); //Get the user id associated with the parameter

        if (userIdParam == null){
            throw new RuntimeException("Cannot update user with non-existent id!");
        }

        boolean isPresentInUsers = userRepository.existsById(userIdParam) ;

        if (!isPresentInUsers){
            throw new RuntimeException("Cannot update non-existent user!");
        }


        /* JPARepository.save will call merge in this case (since user already exists):
        fetch the existing entity from entityManagerFactory...
        finally propagate the changes to the database by calling update query.
         */
        userRepository.save(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PatchMapping("/passive({id}")
    public ResponseEntity<User> setUserPassive(@PathVariable Long id){

        //Attempt to find user by id
        User foundUser = userRepository.findById(id).orElseThrow();

        //If not found, an exception has been thrown.
        //Below this line is safe...
        foundUser.setActive(false);
        foundUser.notifyActivity();
        userRepository.save(foundUser);

        return new ResponseEntity<>(foundUser, HttpStatus.CREATED);

    }

    private void notifyUserUpdate(User oldUser, User newUser){
        System.out.println( "Old: " + oldUser.toString() + "\nNew: " + newUser.toString() );
    }


}

