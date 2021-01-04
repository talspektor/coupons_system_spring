package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.job.DailyJob;
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
		// Start the daily job
//		DailyJob job = context.getBean(DailyJob.class);
//		Thread thread = new Thread(job);
//		thread.start();
		//**********************
		// Login the clients
		AdminService adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
		CompanyService companyService = (CompanyService) loginManager.login("company_1@mail.com", "pass_1", ClientType.COMPNY);
		CustomerService customerService = (CustomerService) loginManager.login("customer_1@email.com", "pass_1", ClientType.CUSTOMER);
		// ADMINISTRATOR
		context.getBean(TestAdmin.class).test(adminService, companyService, customerService);
		// COMPANY
		context.getBean(TestCompany.class).test(adminService, companyService, customerService);
		// CUSTOMER
		context.getBean(TestCustomer.class).test(adminService, companyService, customerService);
		
//		cleenClose(job);	
	}
	
	private void cleenClose(DailyJob job) throws CouponSystemException {
		// Stop the daily job
		job.stop();
	}
}
