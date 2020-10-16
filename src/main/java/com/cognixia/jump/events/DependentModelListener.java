package com.cognixia.jump.events;

import com.cognixia.jump.model.Dependent;
import com.cognixia.jump.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class DependentModelListener extends AbstractMongoEventListener<Dependent> {

    private SequenceGeneratorService sequenceGenerator;

    /**
     * Overloaded Constructor
     * @author David Morales
     * @param sequenceGenerator a sequence generator service instance
     */

    @Autowired
    public DependentModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    /**
     * Auto increments the address id before converting
     * @author David Morales
     * @param event the event that occurs before converting for the User model
     */

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Dependent> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Dependent.SEQUENCE_NAME));
    }
}
