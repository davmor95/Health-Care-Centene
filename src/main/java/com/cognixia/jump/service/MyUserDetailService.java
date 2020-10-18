package com.cognixia.jump.service;

import com.cognixia.jump.model.MyUserDetails;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The custom user details service. 
 * @author David Morales
 * @version v1 (08/24/2020)
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepo userRepository;
    /**
     * Loads the user by their user name.
     * @author David Morales
     * @param userName the user name to search for
     * @return UserDetails - the user details based on the user name
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> userFound = userRepository.findByUserName(userName);
        System.out.println("User found: " + userFound);
        if(!userFound.isPresent()) {
            throw new UsernameNotFoundException("Email " + userName + " does not exist");
        }
        return new MyUserDetails(userFound.get());
    }
}
