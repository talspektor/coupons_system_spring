package app.core.test;


import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class TestAdmin implements TestClient {

	@Override
	public void test(AdminService adminService, CompanyService companyService, CustomerService customerService) throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Admin");
		try {
			Company company = new Company("company_2", "company_2@mail.com", "pass_1");
			adminService.addCompany(company);
			System.out.println(adminService.getCompany(company.getId()));
			System.out.println(adminService.getAllCompanies());
//			
			adminService.deleteCoumpany(company.getId());
			
			Customer customer = new Customer("customer_name_2", "customer_lastName_2", "customer_2@email.com", "pass_1");
			adminService.addCustomer(customer);
			System.out.println(adminService.getCustomer(company.getId()));
			System.out.println(adminService.getAllCustomer());
			
//			adminService.deleteCustomer(1L);
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
		
	}
}
