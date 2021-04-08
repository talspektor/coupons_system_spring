package app.core.controllers.login;

public class LoginItem {
	private String token;

	public LoginItem(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
