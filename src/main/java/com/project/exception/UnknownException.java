package com.project.exception;

public class UnknownException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public UnknownException(String mesg) {
		super(mesg);
	}
}
