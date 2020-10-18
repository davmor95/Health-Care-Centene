package com.cognixia.jump.controller;

import com.cognixia.jump.exceptions.ResourceAlreadyExistsException;
import com.cognixia.jump.exceptions.ResourceNotFoundException;
import com.cognixia.jump.model.Dependent;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.EnrolleeRepo;
import com.cognixia.jump.repo.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller for Users
 * @author David Morales
 * @version v1 (10/14/20)
 */
@RequestMapping("/api")
@RestController
public class EnrolleeController {
    @Autowired
    EnrolleeRepo service;

    @Autowired
    private MongoOperations mongoTemplate;

    @GetMapping("/enrollees")
    @ApiOperation(value = "",
            notes = "Retrieve all enrollees in the database. \n"
                    + "Usage: looks up a list of all enrollees in the database\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): None",
            response = List.class, produces = "application/json")
    public List<Enrollee> getEnrollees() {
        return service.findAll();
    }
    @GetMapping("/dependents/{firstName}/firstName/{lastName}/lastName")
    @ApiOperation(value = "",
            notes = "Retrieve all dependent from desired enrollee in the database. \n"
                    + "Usage: looks up dependents from a specific enrollee in the database\n" +
                    "Author(s): David Morales\n" +
                    "Exception(s): None",
            response = List.class, produces = "application/json")
    public List<Dependent> getDependentFromEnrollee(@PathVariable String firstName, @PathVariable String lastName) throws ResourceNotFoundException {
        Optional<Enrollee> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("Enrollee with name " + firstName + " " + lastName + " does not exists");
        }
        Enrollee enrollee = found.get();
        List<Dependent> dependentList = enrollee.getDependents();
        return dependentList;
    }


    @PostMapping("/add/enrollee")
    @ApiOperation(value = "",
            notes = "Adds a enrollee to the database.\n" +
                    "Usage: provide a new enrollee to add to the database\n" +
                    "Author(s): David Morales\n" +
                    "Exceptions(s): ResourceAlreadyExistsExcpetion is thrown when",
            response = ResponseEntity.class)
    public ResponseEntity<Enrollee> addEnrollee(@RequestBody Enrollee newUser) throws ResourceAlreadyExistsException {
        if (service.existsByFirstNameAndLastName(newUser.getFirstName(), newUser.getLastName())) {
            throw new ResourceAlreadyExistsException("This user with name " + newUser.getFirstName() + " " + newUser.getLastName() + " already exists.");
        } else {
            Enrollee added = service.insert(newUser);
            return ResponseEntity.status(200).body(added);
        }

    }

    @PatchMapping("/patch/modify/enrollee/dependents")
    @ApiOperation( value = "",
            notes = "Updates the dependents of an enrollee.\n"
                    + "Usage: provide a map that holds a user role to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the role does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<Enrollee> patchDependent(@RequestBody Map<String, Object> modifyEnrollee) throws ResourceNotFoundException, JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
        String firstName = (String) modifyEnrollee.get("firstName");
        String lastName = (String) modifyEnrollee.get("lastName");
        List<String> dependents = (ArrayList<String>) modifyEnrollee.get("dependents");
        Optional<Enrollee> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }


        Enrollee updated = service.patch(firstName, lastName, "dependents", dependents);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }

    @PatchMapping("/patch/enrollee/status")
    @ApiOperation( value = "",
            notes = "Updates the status of an enrollee.\n"
                    + "Usage: provide a map that holds the current status of the enrollee to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the role does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<Enrollee> patchStatus(@RequestBody Map<String, Object> modifyEnrollee) throws ResourceNotFoundException, JsonProcessingException {
        String firstName = (String) modifyEnrollee.get("firstName");
        String lastName = (String) modifyEnrollee.get("lastName");
        Boolean status = (Boolean) modifyEnrollee.get("activationStatus");
        Optional<Enrollee> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }


        Enrollee updated = service.patch(firstName, lastName, "activationStatus", status);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }

    @PatchMapping("/patch/enrollee/phoneNumber")
    @ApiOperation( value = "",
            notes = "Updates the phonenumber of an enrollee.\n"
                    + "Usage: provide a map that holds the current phonenumber of the enrollee to update in the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the role does not match an existing user in the database",
            response = ResponseEntity.class)
    public ResponseEntity<Enrollee> patchPhoneNumber(@RequestBody Map<String, Object> modifyEnrollee) throws ResourceNotFoundException, JsonProcessingException {
        String firstName = (String) modifyEnrollee.get("firstName");
        String lastName = (String) modifyEnrollee.get("lastName");
        String phoneNumber = (String) modifyEnrollee.get("phoneNumber");
        Optional<Enrollee> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("User with name + " + firstName + " " + lastName + " is not found");
        }


        Enrollee updated = service.patch(firstName, lastName, "phoneNumber", phoneNumber);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/delete/enrollee/{firstName}/firstName/{lastName}/lastName")
    @ApiOperation( value = "",
            notes = "Deletes an enrollee from the database.\n"
                    + "Usage: provide firstname and lastname to remove an enrollee from the database\n"
                    + "Author(s): David Morales\n"
                    + "Execption(s): ResourceNotFoundException is thrown when the firstname and lastname does not match an existing enrollee in the database",
            response = ResponseEntity.class, produces = "application/json")
    public ResponseEntity<Enrollee> deleteEnrollee(@PathVariable String firstName, @PathVariable String lastName) throws ResourceNotFoundException {
        Optional<Enrollee> found = service.findByFirstNameAndLastName(firstName, lastName);
        if(!found.isPresent()) {
            throw new ResourceNotFoundException("Enrollee with name " + firstName + lastName + " does not exist.");
        }
        service.deleteByFirstNameAndLastName(firstName, lastName);
        return new ResponseEntity<>(found.get(), HttpStatus.ACCEPTED);
    }

}
