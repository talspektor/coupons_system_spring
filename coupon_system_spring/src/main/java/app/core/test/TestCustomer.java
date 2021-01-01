package app.core.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CustomerService;

@Component
public class TestCustomer implements TestClient {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	AdminService adminService;

	@Override
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Customer");
		try {
			Customer customer = new Customer("customer_1", "customer_1_lastName", "1@email.com", "pass_1");
//			adminService.addCustomer(customer);
			customerService.login("1@email.com", "pass_1");
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
