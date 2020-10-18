package com.cognixia.jump.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Model for Users
 * @author David Morales
 * @version v2 (10/14/20)
 */
@Document(collection = "users")
public class User implements Serializable {
    private static final long serialVersionUID = -4458870115303573931L;

    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    public enum Role {
        ROLE_USER, ROLE_ADMIN
    }

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Role userRole;
    private String userName;
    private String password;


    /**
     * The default constructor.
     * @author David Morales
     */
    public User() {
        this( "N/A","N/A", Role.ROLE_USER, "N/A", "N/A");
    }

    /**
     * Overloaded Constructor
     * @param firstName
     * @param lastName
     * @param userRole
     * @param userName
     * @param password
     */

    public User(String firstName, String lastName, Role userRole, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Retrieves the user id
     * @return Long - userId
     */

    public Long getId() {
        return id;
    }

    /**
     * Updates the user id
     * @param id must provide the desire id to update
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves user's firstname
     * @return
     */

    public String getFirstName() {
        return firstName;
    }

    /**
     * Updates user's firstname
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves user's lastname
     * @return - give string to be updated
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Updates user's lastnames
     * @param lastName - give lastname to be updated
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the type of role the user is
     * @return Role - user's role
     */

    public Role getUserRole() {
        return userRole;
    }

    /**
     * Change the user's role either from ADMIN to USER or vice versa
     * @param userRole must state whether its ROLE_ADMIN or ROLE_USER
     */

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * Retrieve username
     * @return a string
     */

    public String getUserName() {
        return userName;
    }

    /**
     * update the username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * retrieve the current password
     * @return
     */

    public String getPassword() {
        return password;
    }

    /**
     * update the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
