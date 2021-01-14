package app.core.test;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.AdminService;
import app.core.services.CustomerService;

@Component
@Transactional
public class TestCustomer implements TestClient {
	

	private AdminService adminService;

	private CustomerService customerService;
	@Autowired
	private LoginManager loginManager;
	
	public void login() throws CouponSystemException {
		adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
		Customer customer = adminService.getAllCustomer().get(0);
		if(customer == null) {
			System.out.println("***** No customers in database!!! you can't continue test... *****");
		}
		customerService = (CustomerService) loginManager.login(customer.getEmail(), customer.getPassword(), ClientType.CUSTOMER);
	}

	public void purchaseCouponTest() throws CouponSystemException {
		System.out.println("============ Test purchase coupon ==============");
		Company company = TestUtils.getRandomCompanyFromDatabase(adminService);
		Coupon coupon = company.getCoupons().get(0);
		customerService.purchaseCoupon(coupon.getId());
		System.out.println("=========================================");
	}
	
	public void getCouponsTest() throws CouponSystemException {
		System.out.println("============ Test get coupons ==============");
		List<Coupon> coupons = customerService.getCoupons();
		System.out.println(coupons);
		System.out.println(coupons.size());
		System.out.println("=========================================");
	}
	
	public void getCouponsByCategoryTest() throws CouponSystemException {
		System.out.println("============ Test get coupons by categoty ==============");
		System.out.println(customerService.getCouponsByCategory(Test.getRandomCategory())); 
		System.out.println("=========================================");
	}
	
	public void getCouponsByPriceLessThenTest() throws CouponSystemException {
		System.out.println("============ Test get coupons by price less then ==============");
		System.out.println(customerService.getCouponsByPriceLessThen(Test.getRandom()));
		System.out.println("=========================================");
	}
	
	public void getCustomerDetailsTest() throws CouponSystemException {
		System.out.println("============ Test get customer details ==============");
		System.out.println(customerService.getCustomerDetails());
		System.out.println("=========================================");
	}
	
	@Override
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Customer");
		purchaseCouponTest();

		getCouponsTest();

		getCouponsByCategoryTest();

		getCouponsByPriceLessThenTest();

		getCustomerDetailsTest();
	}
}
