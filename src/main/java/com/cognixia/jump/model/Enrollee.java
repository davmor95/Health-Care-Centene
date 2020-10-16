package com.cognixia.jump.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for Enrollees
 * @author David Morales
 * @version v2 (10/14/20)
 */

@Document(collection = "enrollee")
public class Enrollee implements Serializable {

    private static final long serialVersionUID = -4458870115303573931L;

    @Transient
    public static final String SEQUENCE_NAME = "enrollee_sequence";

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean activationStatus;
    private String birthDate;
    private String phoneNumber;
    private List<Dependent> dependents;

    /**
     * Default Constructor
     * @author David Morales
     */

    public Enrollee() {
        this("N/A","N/A", false, "N/A", "N/A", new ArrayList<Dependent>());
    }

    /**
     * Overloaded constructor
     * @param firstName Enrollee's name
     * @param lastName Enrollee's lastname
     * @param activationStatus whether the enrollee is active or not
     * @param birthDate date of birth for the enrollee
     * @param phoneNumber enrollee's phone number
     * @param dependents list of dependents under the enrollee
     */

    public Enrollee(String firstName, String lastName, Boolean activationStatus, String birthDate, String phoneNumber, List<Dependent> dependents) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.activationStatus = activationStatus;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.dependents = dependents;
    }

    /**
     * Retrieve the enrollee id
     * @return Long - enrollee id
     */

    public Long getId() {
        return id;
    }

    /**
     * Update the id of the enrollee
     * @param id - give the desire id to be updated
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieve the enrollee's  firstname
     * @return String - enrolle's name
     */

    public String getFirstName() {
        return firstName;
    }
    /**
     * Update the enrollee's fisrtname
     * @param firstName give the name to be updated
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieve the enrollee's  lastname
     * @return
     */

    public String getLastName() {
        return lastName;
    }

    /**
     * Update enrollee's lastname
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the current status
     * @return Boolean - either true or false
     */
    public Boolean getActivationStatus() {
        return activationStatus;
    }

    /**
     * Update the activation status
     * @param activationStatus
     */
    public void setActivationStatus(Boolean activationStatus) {
        this.activationStatus = activationStatus;
    }

    /**
     * Retrieve the DOB of the enrollee
     * @return String - DOB
     */

    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Update enrollee's DOB
     * @param birthDate
     */

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Retrieve enrollee's phone number
     * @return String - in a phone number format (###) ### - ####
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Update enrollee's phonenumber
     * @param phoneNumber - must provide in format (###) ### - ####
     */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Return a list of dependents under the enrollee
     * @return ArrayList - list of dependents
     */

    public List<Dependent> getDependents() {
        return dependents;
    }

    /**
     * Update the list of dependents
     * @param dependents - must give a ArrayList
     */

    public void setDependents(List<Dependent> dependents) {
        this.dependents = dependents;
    }

    @Override
    public String toString() {
        return "Enrollee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", activationStatus=" + activationStatus +
                ", birthDate='" + birthDate + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dependents=" + dependents +
                '}';
    }
}
