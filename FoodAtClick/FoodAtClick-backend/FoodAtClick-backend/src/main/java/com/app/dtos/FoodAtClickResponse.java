package com.app.dtos;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class FoodAtClickResponse {
	public static enum Status {
		SUCCESS, FAIL, ERROR
	}
	
	private Status status;
	private Object data;
	private String message;
	
	// success ctor
	public FoodAtClickResponse(Status status, Object data) {
		this.status = status;
		this.data = data;
	}
	
	// error ctor
	public FoodAtClickResponse(Status status, String message) {
		this.status = status;
		this.message = message;
	}
	
	// fail ctor
	public FoodAtClickResponse(Status status, Object data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	// getters and setters
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static ResponseEntity<FoodAtClickResponse> success() {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.SUCCESS, null));
	}
	
	public static ResponseEntity<FoodAtClickResponse> success(Object data) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.SUCCESS, data));
	}
	
	public static ResponseEntity<FoodAtClickResponse> success(String message, Object data) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.SUCCESS, data, message));
	}
	
	public static ResponseEntity<FoodAtClickResponse> fail(Object data) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.FAIL, data));
	}

	public static ResponseEntity<FoodAtClickResponse> fail(String message, Object data) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.FAIL, data, message));
	}
	
	public static ResponseEntity<FoodAtClickResponse> error(String message) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.ERROR, message));
	}

	public static ResponseEntity<FoodAtClickResponse> error(String message, Object data) {
		return ResponseEntity.ok(new FoodAtClickResponse(Status.ERROR, data, message));
	}
	
}
