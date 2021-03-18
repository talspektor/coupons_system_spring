package app.core.controllers.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import app.core.session.Session;
import app.core.session.SessionContext;

@CrossOrigin
@RestController
public class LogoutController {
	@Autowired
	private SessionContext sessionContext;

	@PostMapping("/logout")
	public void logout(@RequestHeader String token) {
		System.out.println("logout");
		Session session = sessionContext.getSession(token);
		sessionContext.invalidate(session);
	}
}
