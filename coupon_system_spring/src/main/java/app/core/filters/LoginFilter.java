package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import app.core.session.SessionContext;


public class LoginFilter implements Filter {
	
	private SessionContext sessionContext;
	
	public LoginFilter(SessionContext sessionContext) {
		super();
		this.sessionContext = sessionContext;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("doFilter");
		HttpServletRequest req = (HttpServletRequest)request;
		String token = req.getHeader("token");
		System.out.println("token: " + token);

		if(token != null && sessionContext.getSession(token) != null) {
			chain.doFilter(request, response);
			System.out.println("session is good");
			return;
		}
		//TODO: tech debt - remove if block when develop is finished
		if (req.getMethod().equals("OPTIONS")) {
			chain.doFilter(request, response);
			System.out.println("preflight request");
			return;
		}
		
		System.err.println("doFilter fail: " + req.getMethod());
		HttpServletResponse res = (HttpServletResponse) response;
		//TODO: tech debt - remove line when develop is finished
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Headers", "*");
		res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");
	}

}
