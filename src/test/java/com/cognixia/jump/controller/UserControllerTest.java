package com.cognixia.jump.controller;

import com.cognixia.jump.model.Dependent;
import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Configured a series of unit test to evaluate each function under the User
 * controller class
 * @author David Morales
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private final String STARTING_URI = "http://localhost:8080/api";
    /**
     * Mocks the UserRepo therefore the test can simulate as if we are actually
     * making a connection to the databse
     */
    @MockBean
    private UserRepo repo;
    /**
     * Mocks the "PostMan", call all web request as if we were actually using PostMan
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Test to get all users from the mongodb database
     * @throws Exception
     */

    @Test
    public void getUsers() throws Exception {
        String uri = STARTING_URI + "/users";
        List<User> users = Arrays.asList(
                new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "sylmar123")
        );
        when( repo.findAll()).thenReturn(users);
        mockMvc.perform(get(uri))
                .andDo(print())
                .andExpect(status().isOk());

    }

    /**
     * Test if the endpoint will return by a User with the given ID
     * @throws Exception
     */

    @Test
    public void getUserById() throws Exception {
        String uri = STARTING_URI + "/user/{id}";
        User user = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "sylmar123");
        long id = 1L;
        when( repo.findById(id) ).thenReturn(Optional.of(user));

        mockMvc.perform( get(uri, id) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType( MediaType.APPLICATION_JSON) );

    }

    /**
     * Test if the endpoint will add a User to the mock repo
     * @throws Exception
     */
    @Test
    public void addUser() throws Exception {
        String uri = STARTING_URI + "/add/user";
        User user = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "sylmar123");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        when( repo.existsByFirstNameAndLastName(firstName, lastName)).thenReturn(false);
        when( repo.insert(any(User.class))).thenReturn(user);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    /**
     * Mocks the repo therefore we can test if a the delete operation works on the database
     * @throws Exception
     */

    @Test
    public void deleteUser() throws Exception {
        String uri = STARTING_URI + "/delete/user/{firstName}/firstName/{lastName}/lastName";
        User user = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "sylmar123");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        when( repo.existsByFirstNameAndLastName(firstName, lastName) ).thenReturn(true);

        doNothing().when(repo).deleteByFirstNameAndLastName(firstName, lastName);

        mockMvc.perform( delete(uri, firstName, lastName)
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(user) ))
                .andExpect( status().isOk() );
    }

    /**
     * Test if patch request will modify the userRole of the user
     * @throws Exception
     */

    @Test
    public void patchUserRole() throws Exception {
        String uri = STARTING_URI + "/patch/user/role";

        User create = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "sylmar123");
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        User updated = new User("David", "Morales", User.Role.ROLE_ADMIN, "davmor95", "sylmar123");
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "userRole", User.Role.ROLE_ADMIN)).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());

    }

    /**
     * Test if the patch request will modify the userName
     * @throws Exception
     */
    @Test
    public void patchUserName() throws Exception {
        String uri = STARTING_URI + "/patch/user/userName";

        User create = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "123");
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        User updated = new User("David", "Morales", User.Role.ROLE_USER, "hello4ever", "123");
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "userName", "hello4ever")).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());
    }

    /**
     * Test if the patch request will modify the password of the desired user
     * @throws Exception
     */
    @Test
    public void patchPassword() throws Exception {
        String uri = STARTING_URI + "/patch/user/password";

        User create = new User("David", "Morales", User.Role.ROLE_USER, "davmor95", "123");
        String firstName = create.getFirstName();
        String lastName = create.getLastName();
        User updated = new User("David", "Morales", User.Role.ROLE_USER, "hello4ever", "doggy1!");
        when( repo.findByFirstNameAndLastName(firstName, lastName) ).thenReturn(Optional.of(create));
        when( repo.patch(firstName, lastName, "password", "doggy1!")).thenReturn(updated);

        mockMvc.perform(patch(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updated)))
                .andDo(print());
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