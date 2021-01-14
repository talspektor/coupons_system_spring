package app.core.test;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

public interface TestClient {
	void test() throws CouponSystemException;
}
