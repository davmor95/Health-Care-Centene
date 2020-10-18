package com.cognixia.jump.controller;

import com.cognixia.jump.exceptions.ResourceAlreadyExistsException;
import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for Users
 * @author David Morales
 * @version v1 (10/14/20)
 */
@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserRepo service;

    @Autowired
    private MongoOperations mongoTemplate;

    @GetMapping("/users")
    @ApiOperation(value = "",
            notes = "Retrieve all users in the database. \n"
                    + "Usage: looks up a list of all users in the database\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): None",
            response = List.class, produces = "application/json")
    public List<User> getUsers() {
        return service.findAll();
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "",
            notes = "Retrieve a user pertaining to an id. \n" +
                    "Usage: provide an id to look up a user in the database\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): ResourceNotFoundException is thrown when the id does not match an exisiting user in the database",
            response = User.class, produces = "application/json")
    public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<User> found = service.findById(id);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with id= " + id + " is not found");
        }

        return found.get();
    }

    @PostMapping("/add/user")
    @ApiOperation(value = "",
            notes = "Adds a user to the database.\n" +
                    "Usage: provide a new user to add to the database\n" +
                    "Author(s): David Morales\n" +
                    "Exceptions(s): ResourceAlreadyExistsExcpetion is thrown when",
            response = ResponseEntity.class)
    public ResponseEntity<User> addUser(@RequestBody User newUser) throws ResourceAlreadyExistsException {
        if (service.existsByFirstNameAndLastName(newUser.getFirstName(), newUser.getLastName())) {
            throw new ResourceAlreadyExistsException("This user with name " + newUser.getFirstName() + " " + newUser.getLastName() + " already exists.");
        } else {
            User added = service.insert(newUser);
            return ResponseEntity.status(200).body(added);
        }

    }

    @DeleteMapping("/delete/user/{firstName}/firstName/{lastName}/lastName")
    @ApiOperation(value = "",
            notes = "Delete a user from the database.\n" +
                    "Usage: provide lastname and firstname to remove user from database.\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): ResourceNotFoundException is thrown when the first and last name does not match an existing user in the database",
            response = ResponseEntity.class, produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable String firstName, @PathVariable String lastName) throws ResourceNotFoundException {
        Optional<User> found = service.findByFirstNameAndLastName(firstName, lastName);
        if (found.isPresent()) {
            service.deleteByFirstNameAndLastName(firstName, lastName);
            return ResponseEntity.status(200).body("Deleted student with name " + firstName + " " + lastName);
        } else {
            throw new ResourceNotFoundException("User with name " + firstName + " " + lastName + " not found");
        }
    }

    @PatchMapping("/patch/user/role")
    @ApiOperation( value = "",
            notes = "Updates the role of a user.\n"
                    + "Usage: provide a map that holds a user role to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the role does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> patchUserRole(@RequestBody Map<String, String> userRole) throws ResourceNotFoundException {
        String firstName = userRole.get("firstName");
        String lastName = userRole.get("lastName");
        String newRole = userRole.get("userRole");
        Optional<User> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }

        User updated = service.patch(firstName, lastName, "userRole", newRole);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }

    @PatchMapping("/patch/user/userName")
    @ApiOperation( value = "",
            notes = "Updates the username of a user.\n"
                    + "Usage: provide a map that holds the username to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the username does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> patchUserName(@RequestBody Map<String, String> userRole) throws ResourceNotFoundException {
        String firstName = userRole.get("firstName");
        String lastName = userRole.get("lastName");
        String newRole = userRole.get("userName");
        Optional<User> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }

        User updated = service.patch(firstName, lastName, "userName", newRole);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }

    @PatchMapping("/patch/user/password")
    @ApiOperation( value = "",
            notes = "Updates the username of a user.\n"
                    + "Usage: provide a map that holds the username to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the username does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<User> patchPassword(@RequestBody Map<String, String> userRole) throws ResourceNotFoundException {
        String firstName = userRole.get("firstName");
        String lastName = userRole.get("lastName");
        String newRole = userRole.get("password");
        Optional<User> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }

        User updated = service.patch(firstName, lastName, "password", newRole);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }



}
