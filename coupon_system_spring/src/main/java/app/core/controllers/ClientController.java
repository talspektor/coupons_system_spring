package app.core.controllers;


import app.core.exceptions.CouponSystemException;

public interface ClientController {
	boolean login(String email, String password) throws CouponSystemException;
}
