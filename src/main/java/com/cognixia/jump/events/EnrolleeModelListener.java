package com.cognixia.jump.events;

import com.cognixia.jump.model.Enrollee;
import com.cognixia.jump.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

/**
 * Model Listener for Enrollee
 * @author David Morales
 * @version v1 (10/14/20)
 */

@Component
public class EnrolleeModelListener extends AbstractMongoEventListener<Enrollee> {
    private SequenceGeneratorService sequenceGenerator;

    /**
     * Overloaded Constructor
     * @author David Morales
     * @param sequenceGenerator a sequence generator service instance
     */

    @Autowired
    public EnrolleeModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * Auto incremenents the address id before converting
     * @author David Morales
     * @param event the event that occurs before converting for the Enrollee Model
     */

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Enrollee> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Enrollee.SEQUENCE_NAME));
    }
}
