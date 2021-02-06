package app.core.controllers;

public class ResponseItem<T> {
	private T data;
	private String message;
	
	public ResponseItem(T data) {
		this.data = data;
	}
	
	public ResponseItem(T data, String message) {
		super();
		this.data = data;
		this.message = message;
	}

	public T getDate() {
		return data;
	}

	public String getMessage() {
		return message;
	}
	
	
}
