package com.cognixia.jump.repo;

import com.cognixia.jump.model.Dependent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Dependents
 * @author David Morales
 * @version v3 (10/14/20)
 */
@Repository
public interface DependentRepo extends MongoRepository<Dependent, Long> {
    Boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
