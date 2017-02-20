package net.discussions.core.model;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private HttpStatus status;
	private String message;
	private T content;

	public Response() {
	}
	
	public Response(HttpStatus status) {
		this.status = status;
	}
	
	public Response(HttpStatus status, T content) {
		this.status = status;
		this.content = content;
	}
	
	public Response(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(HttpStatus status, String message, T content) {
		this.status = status;
		this.message = message;
		this.content = content;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
