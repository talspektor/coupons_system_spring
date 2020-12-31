package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

@Component
@Transactional
public class TestAdmin implements TestClient {

	@Autowired
	private AdminService adminService;
	
	@Override
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Admin");
		try {
//			adminService.login("wronrEmail", "wrongPassword");
//			adminService.login("com.admin@admin", "admin");
			
//			Company company = new Company("company_1", "company_1@mail.com", "pass_1");
//			adminService.addCompany(company);
//			System.out.println(adminService.getCompany(1L));
//			System.out.println(adminService.getAllCompanies());
//			
//			adminService.deleteCoumpany(1L);
			
			Customer customer = new Customer("customer_name_1", "customer_lastName_1", "customer_1@email.com", "pass_1");
			adminService.addCustomer(customer);
			System.out.println(adminService.getCustomer(2L));
			System.out.println(adminService.getAllCustomer());
			
			adminService.deleteCustomer(1L);
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
		
	}
}
