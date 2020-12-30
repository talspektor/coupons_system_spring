package app.core.services;

import app.core.exceptions.CouponSystemException;

public interface ClientService {
	public boolean login(String email, String password) throws CouponSystemException;
}
