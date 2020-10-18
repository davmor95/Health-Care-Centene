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

@ExtendWith(SpringExtension.class)
@WebMvcTest(EnrolleeController.class)
public class EnrolleeControllerTest {

    private final String STARTING_URI = "http://localhost:8080/api";
    @MockBean
    private EnrolleeRepo repo;

    @Autowired
    private MockMvc mockMvc;
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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}