package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.AdminService;

@Component
@Transactional
public class TestAdmin {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private LoginManager loginManager;

	public void loginTest() throws CouponSystemException {
		adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
	}
	
	public void addCompanyTest() throws CouponSystemException {
		System.out.println("========== Test add Company ==============");
		Company company = TestUtils.getRandomNewCompany();
		System.out.println(company);
		adminService.addCompany(company);
		System.out.println("===========================================");
	}
	
	public void getCompanyTest() throws CouponSystemException {
		System.out.println("========== Test get Company ==============");
		Company company = TestUtils.getRandomCompanyFromDatabase(adminService);
		System.out.println(adminService.getCompany(company.getId()));
		System.out.println("===========================================");
	}
	
	public void getAllCompaniesTest() throws CouponSystemException {
		System.out.println("========== Test get all Companies ==============");
		System.out.println(adminService.getAllCompanies());
		System.out.println("===========================================");
	}
	
	public void addCustomerTest() throws CouponSystemException {
		System.out.println("========== Test add Customer ==============");
		Customer customer = TestUtils.getRandomNewCustomer();
		adminService.addCustomer(customer);
		System.out.println(customer);
		System.out.println("===========================================");
	}
	
	public void getCustomerTest() throws CouponSystemException {
		System.out.println("========== Test get Customer ==============");
		Customer customer = TestUtils.getRandomCustomerFromDatabase(adminService);
		System.out.println(adminService.getCustomer(customer.getId()));
		System.out.println(customer);
		System.out.println("===========================================");
	}
	
	public void getAllCustomersTest() throws CouponSystemException {
		System.out.println("========== Test get all Customeres ==============");
		System.out.println(adminService.getAllCustomer());
		System.out.println("===========================================");
	}
	
	public void deleteCompanyTest() throws CouponSystemException {
		System.out.println("========== Test delete Company ==============");
		Company company = TestUtils.getRandomNewCompany();
		adminService.addCompany(company);
		company = adminService.getCompanyByName(company.getName());
		adminService.deleteCoumpany(company.getId());
		System.out.println("===========================================");
	}
	
	public void deleteCustomerTest() throws CouponSystemException {
		System.out.println("========== Test delete Customer ==============");
		adminService.addCustomer(TestUtils.getRandomNewCustomer());
		System.out.println("===========================================");
	}
}
