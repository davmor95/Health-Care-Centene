package com.cognixia.jump.repo.custom;

import com.cognixia.jump.model.Enrollee;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface EnrolleeRepoCustomUpdate {
    Enrollee patch(String firstName, String lastName, String field, Object value);
}
