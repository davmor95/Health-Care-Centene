package com.cognixia.jump.controller;

import com.cognixia.jump.exceptions.ResourceAlreadyExistsException;
import com.cognixia.jump.model.Dependent;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.DependentRepo;
import com.cognixia.jump.repo.UserRepo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Users
 *
 * @author David Morales
 * @version v1 (10/14/20)
 */
@RequestMapping("/api")
@RestController
public class DependentController {
    @Autowired
    DependentRepo service;

    @Autowired
    private MongoOperations mongoTemplate;


    @GetMapping("/dependents")
    @ApiOperation(value = "",
            notes = "Retrieve all dependents in the database. \n"
                    + "Usage: looks up a list of all dependents in the database\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): None",
            response = List.class, produces = "application/json")
    public List<Dependent> getDependents() {
        return service.findAll();
    }

    @PostMapping("/add/dependent")
    @ApiOperation(value = "",
            notes = "Adds a dependent to the database.\n" +
                    "Usage: provide a new dependent to add to the database\n" +
                    "Author(s): David Morales\n" +
                    "Exceptions(s): ResourceAlreadyExistsExcpetion is thrown when",
            response = ResponseEntity.class)
    public ResponseEntity<Dependent> addUser(@RequestBody Dependent newUser) throws ResourceAlreadyExistsException {
        if (service.existsByFirstNameAndLastName(newUser.getFirstName(), newUser.getLastName())) {
            throw new ResourceAlreadyExistsException("This user with name " + newUser.getFirstName() + " " + newUser.getLastName() + " already exists.");
        } else {
            Dependent added = service.insert(newUser);
            return ResponseEntity.status(200).body(added);
        }

    }
}
