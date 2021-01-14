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
public class TestAdmin implements TestClient {
	
	private AdminService adminService;
	@Autowired
	private LoginManager loginManager;

	public void loginTest() throws CouponSystemException {
		adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
	}
	
	public void addCompanyTest() throws CouponSystemException {
		System.out.println("========== Test add Company ==============");
		Company company = Test.getRandomNewCompany();
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
		adminService.addCustomer(Test.getRandomNewCustomer());
		System.out.println("===========================================");
	}
	
	public void getCustomerTest() throws CouponSystemException {
		System.out.println("========== Test get Customer ==============");
		Customer customer = adminService.getAllCustomer().get(0);
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
		Company company = Test.getRandomNewCompany();
		adminService.addCompany(company);
		company = adminService.getCompanyByName(company.getName());
		adminService.deleteCoumpany(company.getId());
		System.out.println("===========================================");
	}
	
	public void deleteCustomerTest() throws CouponSystemException {
		System.out.println("========== Test delete Customer ==============");
		adminService.addCustomer(Test.getRandomNewCustomer());
		System.out.println("===========================================");
	}
	
	@Override
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Admin");

		addCompanyTest();

		getCompanyTest();

		getAllCompaniesTest();

		addCustomerTest();

		getCustomerTest();

		getAllCustomersTest();

		deleteCompanyTest();

		deleteCustomerTest();
	}
}
