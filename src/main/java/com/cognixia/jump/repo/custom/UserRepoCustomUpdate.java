package com.cognixia.jump.repo.custom;

import com.cognixia.jump.model.User;

public interface UserRepoCustomUpdate {
    User patch(String firstName, String lastName, String field, Object value);
}
