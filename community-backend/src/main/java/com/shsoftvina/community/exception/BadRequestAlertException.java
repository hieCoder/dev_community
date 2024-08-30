package com.shsoftvina.community.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

@SuppressWarnings("java:S110") // Inheritance tree of classes should not be too deep
public class BadRequestAlertException extends ErrorResponseException {
	
	private static final long serialVersionUID = 1L;
	
	private final String entityName;
	
	private final String errorKey;
	
	public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
		this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
	}
	
	public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
		super(
				HttpStatus.BAD_REQUEST,
				ProblemDetailWithCause.ProblemDetailWithCauseBuilder
						.instance()
						.withStatus(HttpStatus.BAD_REQUEST.value())
						.withType(type)
						.withTitle(defaultMessage)
						.withProperty("message", "error." + errorKey)
						.withProperty("params", entityName)
						.build(),
				null
		);
		this.entityName = entityName;
		this.errorKey = errorKey;
	}
	
	public String getEntityName() {
		return entityName;
	}
	
	public String getErrorKey() {
		return errorKey;
	}
	
	public BadRequestAlertException(ErrorEnum error, Object data) {
		super(
				HttpStatus.BAD_REQUEST,
				ProblemDetailWithCause.ProblemDetailWithCauseBuilder
						.instance()
						.withStatus(HttpStatus.BAD_REQUEST.value())
						.withProperty("message", error.getMessage())
						.withProperty("data", data)
						.build(),
				null
		);
		this.entityName = error.getEntityName();
		this.errorKey = error.getErrorKey();
	}
	
	public BadRequestAlertException(ErrorEnum errorEnum) {
		this(errorEnum, null);
	}
	
	public ProblemDetailWithCause getProblemDetailWithCause() {
		return (ProblemDetailWithCause) this.getBody();
	}
}
