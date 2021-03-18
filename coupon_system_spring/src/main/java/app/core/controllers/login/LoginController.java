package app.core.controllers.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.ClientService;
import app.core.session.Session;
import app.core.session.SessionContext;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	private SessionContext sessionContext;
	@Autowired
	private LoginManager loginManager;
	
	@PostMapping("/login/{type}/{email}/{password}")
	public String login(@PathVariable ClientType type, @PathVariable String email, @PathVariable String password) {
		System.out.println("LoginController login");
		try {
			ClientService service = loginManager.login(email, password, type);
			if (service != null) {
				Session session = sessionContext.createSession();
				session.setAttribute("email", email);
				session.setAttribute("service", service);
				return session.token;
			}
			throw new CouponSystemException(HttpStatus.BAD_REQUEST, "login fail :(");
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
