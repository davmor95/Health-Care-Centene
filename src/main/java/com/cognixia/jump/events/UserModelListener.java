package com.cognixia.jump.events;

import com.cognixia.jump.model.User;
import com.cognixia.jump.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * Model Listener for User
 * @author David Morales
 * @version v1 (10/14/20)
 */
@Component
public class UserModelListener extends AbstractMongoEventListener<User> {

    private SequenceGeneratorService sequenceGenerator;

    /**
     * Overloaded Constructor
     * @author David Morales
     * @param sequenceGenerator a sequence generator service instance
     */

    @Autowired
    public UserModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * Auto increments the address id before converting
     * @author David Morales
     * @param event the event that occurs before converting for the User model
     */

    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
    }
}
