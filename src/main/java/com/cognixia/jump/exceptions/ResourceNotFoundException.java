package com.cognixia.jump.exceptions;

/**
 * The custom exception for a resource that was not found.
 * @author David Morales
 * @version v1 (10/14/2020)
 */
public class ResourceNotFoundException extends Exception{
	private static final long serialVersionUID = 5777514695224236672L;
	/**
	 * The overloaded constructor.
	 * @author David Morales
	 * @param message the custom error message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
