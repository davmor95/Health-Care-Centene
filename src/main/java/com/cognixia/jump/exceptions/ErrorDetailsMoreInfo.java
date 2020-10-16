package com.cognixia.jump.exceptions;

import java.util.Date;

/**
 * The model for ErrorDetailsMoreInfo.
 * @author David Morales
 * @version v1 (10/14/2020)
 */
public class ErrorDetailsMoreInfo extends ErrorDetails {
	private Integer status;
	/**
	 * The overloaded constructor.
	 * @author David Morales
	 * @param timestamp the current date the error occurred
	 * @param message the error message
	 * @param details the details of the error
	 * @param status the HTTP status code numeric value
	 */
	public ErrorDetailsMoreInfo(Date timestamp, String message, String details, Integer status) {
		super(timestamp, message, details);
		this.status = status;
	}
	/**
	 * Retrieves the HTTP status code numeric value.
	 * @author David Morales
	 * @return Integer - the HTTP status code numeric value
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * Updates the HTTP status code numeric value.
	 * @author David Morales
	 * @param status the HTTP status code numeric value
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * Creates a string representation of an error details more info.
	 * @author David Morales
	 * @return String - a string representation of an error details more info
	 */
	@Override
	public String toString() {
		return super.toString() + "ErrorDetailsMoreInfo [status=" + status + "]";
	}

}
