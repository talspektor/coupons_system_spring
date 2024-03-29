package app.core.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
@Transactional
public class TestCustomer {
	

	private AdminService adminService;

	private CustomerService customerService;
	private CompanyService companyService;
	@Autowired
	private LoginManager loginManager;
	
	public void login() throws CouponSystemException {
		adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMIN);
		Customer customer = TestUtils.getRandomCustomerFromDatabase(adminService);
		Company company = TestUtils.getRandomCompanyFromDatabase(adminService);
		if(company == null) {
			System.out.println("*****  No companies in database!!! you can't continue test... ******");
			return;
		}
		companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);
		if(customer == null) {
			System.out.println("***** No customers in database!!! you can't continue test... *****");
		}
		customerService = (CustomerService) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
	}

	public void purchaseCouponTest() throws CouponSystemException {
		System.out.println("============ Test purchase coupon ==============");
		Coupon coupon = TestUtils.getRandomCouponFromDatabase(adminService);
		if (coupon != null) {
			customerService.purchaseCoupon(coupon.getId());
		}
		System.out.println("");
		System.out.println("=========================================");
	}
	
	public void getCouponsTest() throws CouponSystemException {
		System.out.println("============ Test get coupons ==============");
		List<Coupon> coupons = customerService.getCoupons();
		System.out.println(coupons);
		System.out.println(coupons.size());
		System.out.println("=========================================");
	}
	
	public void getAllCouponsFromDB() throws CouponSystemException {
		System.out.println("============ Test get All coupons from DB ==============");
		List<Coupon> coupons = customerService.getAllDatabaseCoupons();
		System.out.println(coupons);
		System.out.println("There are: " + coupons.size() + " coupons in database");
		System.out.println("=========================================");
	}
	
	public void getCouponsByCategoryTest(Category category) throws CouponSystemException {
		System.out.println("============ Test get coupons by categoty ==============");
		System.out.println(customerService.getCouponsByCategory(category)); 
		System.out.println("=========================================");
	}
	
	public void getCouponsByPriceLessThenTest() throws CouponSystemException {
		System.out.println("============ Test get coupons by price less then ==============");
		System.out.println(customerService.getCouponsByPriceLessThen(TestUtils.getRandom()));
		System.out.println("=========================================");
	}
	
	public void getCustomerDetailsTest() throws CouponSystemException {
		System.out.println("============ Test get customer details ==============");
		System.out.println(customerService.getCustomerDetails());
		System.out.println("=========================================");
	}
}
