package app.core.test;

import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;

@Component
public class TestCompany implements TestClient {

	@Override
	public void test() throws CouponSystemException {
		try {
			
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
