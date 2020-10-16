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
@Document(collection = "dependent")
public class Dependent implements Serializable {
    private static final long serialVersionUID = -4458870115303573931L;
    @Transient
    public static final String SEQUENCE_NAME = "dependent_sequence";

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    /**
     * The default constructor
     */
    public Dependent() {
        this("N/A", "N/A", "N/A");
    }

    /**
     * Overloaded constructor
     * @param firstName
     * @param lastName
     * @param birthDate
     */
    public Dependent(String firstName, String lastName, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    /**
     * Retrieve the id of the dependent
     * @return Long - id
     */

    public Long getId() {
        return id;
    }
    /**
     * Update the id of the dependent
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Retrieve the dependent first name
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Update the dependent first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Retrieve the dependent first name
     * @return String
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * Update the dependent last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Retrieve the DOB of the dependent
     * @return
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Update the birthDate of the dependent
     * @param birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Dependent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
