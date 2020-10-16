package com.cognixia.jump.repo;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repo.custom.UserRepoCustomUpdate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *The repository for Users
 * @author David Morales
 * @version v3 (10/14/20)
 */
@Repository
public interface UserRepo extends MongoRepository<User, Long>, UserRepoCustomUpdate {
    Boolean existsByFirstNameAndLastName(String firstName, String lastName);
    Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
    void deleteByFirstNameAndLastName(String firstName, String lastName);
}
