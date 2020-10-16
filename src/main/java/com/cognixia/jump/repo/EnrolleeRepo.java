package com.cognixia.jump.repo;

import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.repo.custom.EnrolleeRepoCustomUpdate;
import com.cognixia.jump.repo.custom.EnrolleeRepoImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Enrollees
 * @author David Morales
 * @version v3 (10/14/20)
 */
@Repository
public interface EnrolleeRepo extends MongoRepository<Enrollee, Long>, EnrolleeRepoCustomUpdate {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);

    Optional<Enrollee> findByFirstNameAndLastName(String firstName, String lastName);
    void deleteByFirstNameAndLastName(String firstName, String lastName);
}
