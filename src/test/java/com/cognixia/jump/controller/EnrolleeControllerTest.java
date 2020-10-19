package com.cognixia.jump.controller;

import com.cognixia.jump.model.Dependent;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.repo.EnrolleeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Configured a series of unit test to evaluate each function under the Enrollee
 * controller class
 * @author David Morales
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(EnrolleeController.class)
public class EnrolleeControllerTest {

    private final String STARTING_URI = "http://localhost:8080/api";
    /**
     * Mocks the UserRepo therefore the test can simulate as if we are actually
     * making a connection to the databse
     */
    @MockBean
    private EnrolleeRepo repo;
    /**
     * Mocks the "PostMan", call all web request as if we were actually using PostMan
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * Test to get all enrollees from the mongodb database
     * @throws Exception
     */
    @Test
    public void getEnrollees() throws Exception {
        String uri = STARTING_URI + "/enrollees";
        List<Dependent> dependentList = Arrays.asList(
          new Dependent("Willy", "Morales", "03/21/2001")
        );
        List<Enrollee> enrollees = Arrays.asList(
                new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList),
                new Enrollee("Brian", "Morales", false, "05/09/1996", "818-792-0288", dependentList)
        );

        when( repo.findAll() ).thenReturn(enrollees);

        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk());
        verify(repo, times(1)).findAll();
        verifyNoMoreInteractions(repo);


    }

    /**
     * Test if the endpoint will return the dependents from the desired enrollee with the given firstname and lastname
     * @throws Exception
     */
    @Test
    public void getDependentFromEnrollee() throws Exception {
        String uri = STARTING_URI + "/dependents/{firstName}/firstName/{lastName}/lastName";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        Enrollee enrollee = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);

        when( repo.findByFirstNameAndLastName(enrollee.getFirstName(), enrollee.getLastName()) ).thenReturn(Optional.of(enrollee));

        mockMvc.perform( get(uri, enrollee.getFirstName(), enrollee.getLastName()))
                .andExpect( status().isOk())
                .andExpect( content().contentType(MediaType.APPLICATION_JSON));

    }

    /**
     * test the post operation if the service will add the enrollee to the database
     * @throws Exception
     */
    @Test
    public void addUser() throws Exception {
        String uri = STARTING_URI + "/add/enrollee";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        Enrollee create = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        when( repo.existsByFirstNameAndLastName(firstName, lastName)).thenReturn(false);
        when( repo.insert(Mockito.any(Enrollee.class))).thenReturn(create);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(create)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * test if the patch operation will modify the number of dependents under the desired enrollee
     * @throws Exception
     */
    @Test
    public void patchDependent() throws Exception {
        String uri = STARTING_URI + "/patch/modify/enrollee/dependents";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        List<Dependent> dependentList2 = Arrays.asList(
                new Dependent("Brian", "Morales", "05/09/1995")
        );
        Enrollee create = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        Enrollee updated = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList2);
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "dependents", dependentList)).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());
    }

    /**
     * test if the patch operation will modify the activationStatus given the desired enrollee
     * @throws Exception
     */
    @Test
    public void patchStatus() throws Exception {
        String uri = STARTING_URI + "/patch/enrollee/status";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        Enrollee create = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        Enrollee updated = new Enrollee("David", "Morales", false, "01/06/1995", "818-792-0288", dependentList);
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "activationStatus", false)).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());

    }

    /**
     * test if the patch operation will modify the phoneNumber given the desired enrollee
     * @throws Exception
     */
    @Test
    public void patchPhoneNumber() throws Exception {
        String uri = STARTING_URI + "/patch/enrollee/phoneNumber";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        Enrollee create = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        Enrollee updated = new Enrollee("David", "Morales", false, "01/06/1995", "818-792-0288", dependentList);
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "phoneNumber", "818-811-0288")).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());
    }

    /**
     * test if the delete operation will delete the desired enrollee
     * @throws Exception
     */
    @Test
    public void deleteEnrollee() throws Exception {
        String uri = STARTING_URI + "/delete/enrollee/{firstName}/firstName/{lastName}/lastName";
        List<Dependent> dependentList = Arrays.asList(
                new Dependent("Willy", "Morales", "03/21/2001")
        );
        Enrollee create = new Enrollee("David", "Morales", true, "01/06/1995", "818-792-0288", dependentList);
        String firstName = create.getFirstName();
        String lastName = create.getLastName();

        when( repo.existsByFirstNameAndLastName(firstName, lastName)).thenReturn(true);

        doNothing().when(repo).deleteByFirstNameAndLastName(firstName, lastName);

        mockMvc.perform( delete(uri, firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(create) ))
                .andExpect( status().isOk() );

    }
    /**
     * return the object as if it were a json object
     * @param obj
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}