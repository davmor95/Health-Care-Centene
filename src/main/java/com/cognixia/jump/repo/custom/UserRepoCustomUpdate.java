package com.cognixia.jump.repo.custom;

import com.cognixia.jump.model.User;

/**
 * Created this custom interface for the User repository.
 * abstract method for patch operation
 */
public interface UserRepoCustomUpdate {
    User patch(String firstName, String lastName, String field, Object value);
}
