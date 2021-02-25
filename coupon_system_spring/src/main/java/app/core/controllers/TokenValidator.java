package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.session.Session;
import app.core.session.SessionContext;

@Component
public class TokenValidator {
	
	@Autowired
	SessionContext sessionContext;

	public boolean validate(String token) {
		Session session = sessionContext.getSession(token);
		if (session == null) {
			return false;
		}
		return true;
	}
}
