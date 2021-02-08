package app.core.controllers;

import org.springframework.http.ResponseEntity;

import app.core.exceptions.CouponSystemException;

public interface ClientController {
	ResponseEntity<ResponseItem<Boolean>> login(String email, String password) throws CouponSystemException;
}
