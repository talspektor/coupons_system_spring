package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class Test {

	@Autowired
	private LoginManager loginManager;
	@Autowired
	private ApplicationContext context;

	public Test() {
		super();
	}
	@Transactional
	public void testAll() throws CouponSystemException {
		//**********************
		// Login the clients
		AdminService adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
		
		Company company = adminService.getAllCompanies().get(0);
		if(company == null) {
			System.out.println("*****  No companies in database!!! you can't continue test... ******");
			return;
		}
		CompanyService companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPNY);
		
		Customer customer = adminService.getAllCustomer().get(0);
		if(customer == null) {
			System.out.println("***** No customers in database!!! you can't continue test... *****");
		}
		CustomerService customerService = (CustomerService) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
		
		// ADMINISTRATOR
		context.getBean(TestAdmin.class).test(adminService, companyService, customerService);
//		// COMPANY
		context.getBean(TestCompany.class).test(adminService, companyService, customerService);
//		// CUSTOMER
		context.getBean(TestCustomer.class).test(adminService, companyService, customerService);
		
	}
	
}
