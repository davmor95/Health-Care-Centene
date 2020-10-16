package com.cognixia.jump.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The model for Database Sequence.
 * @author David Morales
 * @version v2 (10/14/2020)
 */
@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;

    private long seq;
    
    /**
	 * The default constructor.
	 * @author David Morales
	 */
    public DatabaseSequence() {}
    /**
     * Retrieves the sequence id.
     * @author David Morales
     * @return String - the sequence id
     */
    public String getId() {
        return id;
    }
    /**
     * Updates the sequence id.
     * @author David Morales
     * @param id the sequence id
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Retrieves the sequence value.
     * @author David Morales
     * @return long - the sequence value
     */
    public long getSeq() {
        return seq;
    }
    /**
     * Updates the sequence value.
     * @author David Morales
     * @param seq the sequence value
     */
    public void setSeq(long seq) {
        this.seq = seq;
    }
}
