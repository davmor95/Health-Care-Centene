package com.cognixia.jump.exceptions;

/**
 * The custom exception for a resource that already exists.
 * @author David Morales
 * @version v1 (10/14/2020)
 */
public class ResourceAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 5777514691224236672L;
	/**
	 * The overloaded constructor.
	 * @author David Morales
	 * @param message the custom error message
	 */
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
