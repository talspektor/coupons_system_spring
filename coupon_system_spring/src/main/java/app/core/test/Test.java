package app.core.test;

import java.sql.Date;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
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
//		AdminService adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
//		
//		Company company = adminService.getAllCompanies().get(0);
//		if(company == null) {
//			System.out.println("*****  No companies in database!!! you can't continue test... ******");
//			return;
//		}
//		CompanyService companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPNY);
//		
//		Customer customer = adminService.getAllCustomer().get(0);
//		if(customer == null) {
//			System.out.println("***** No customers in database!!! you can't continue test... *****");
//		}
//		CustomerService customerService = (CustomerService) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
		{
			// ADMINISTRATOR
			TestAdmin testAdmin = context.getBean(TestAdmin.class);
			
			testAdmin.loginTest();
//			testAdmin.addCompanyTest();
//			testAdmin.getCompanyTest();
//			testAdmin.getAllCompaniesTest();
			testAdmin.addCustomerTest();
			testAdmin.getCustomerTest();
			testAdmin.getAllCustomersTest();
			testAdmin.deleteCompanyTest();
			testAdmin.deleteCustomerTest();
		}
		
//		{
//			// COMPANY
//			TestCompany testCompany = context.getBean(TestCompany.class);
//
//			testCompany.login();
//			testCompany.addCouponTest();
//			testCompany.updateCouponTest();
//			testCompany.getCompanyCouponsTest();
//			testCompany.getCoumpanyCouponsByMaxPrice();
//			testCompany.deleteCouponTest();
//			testCompany.getCompanyDetailsTest();
//
//		}
//		
//		{
//			// CUSTOMER
//			TestCustomer testCustomer = context.getBean(TestCustomer.class);
//			
//			testCustomer.login();
//			testCustomer.purchaseCouponTest();
//			testCustomer.getCouponsTest();
//			testCustomer.getCouponsByCategoryTest();
//			testCustomer.getCouponsByPriceLessThenTest();
//			testCustomer.getCustomerDetailsTest();
//		}	
	}
	
	// Helper functions
	
	static int getRandom() {
		return (int)Math.random() + 1000;
	}
	
	static Company getRandomNewCompany() {
		return new Company("companyName_" + getRandom(), "email" + getRandom() +"@email.com", "pass" + getRandom());
	}
	
	static Customer getRandomNewCustomer() {
		return new Customer("customerName_" + getRandom(), "lastName_" + getRandom(), getRandom() + "@email.com", "pass" + getRandom());
	}
	
	static Category getRandomCategory() {
		Category[] categories = {Category.ELECTRICITY, Category.FOOD, Category.SPORTS, Category.VACATION};
		return categories[(int)Math.random() * 4];
	}
	
	static Coupon getRandomNewCoupon(Company company) {
		String title = "title_" + getRandom();
		String description = "desc_" + getRandom();
		Date startDate = Date.valueOf(LocalDate.of(2020, 2, 1));
		Date endDate = Date.valueOf(LocalDate.of(2021, 2, 1));
		int amount = (int)Math.random() + 25;
		double price = (double)Math.random() + 1000;
		String imageUrl = "some_url";
		Category category = getRandomCategory();
		
		return new Coupon(company, category, title, description, startDate, endDate, amount, price, imageUrl);
	}
	
}
