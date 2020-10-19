package com.cognixia.jump.repo.custom;

import com.cognixia.jump.model.Enrollee;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created this custom interface for the enrollee repository.
 * abstract method for patch operation
 */
public interface EnrolleeRepoCustomUpdate {
    Enrollee patch(String firstName, String lastName, String field, Object value);
}
