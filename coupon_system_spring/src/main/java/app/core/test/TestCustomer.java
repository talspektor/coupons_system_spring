package app.core.test;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class TestCustomer implements TestClient {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	AdminService adminService;

	@Override
	@Transactional
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Customer");
		try {
//			Company company = adminService.getCompany(2L);
//			Customer customer = new Customer("customer_1", "customer_1_lastName", "1@email.com", "pass_1");
//			adminService.addCustomer(customer);
//			
//			customerService.login("1@email.com", "pass_1");
//			
//			Date startDate = new Date(2018, 1, 10);
//			Date endDate = new Date(2021, 1, 10);
//			int amount = 5;
//			double price = 10.5;
//			String imageUrl = "some_url";
//			Coupon coupon1 = new Coupon(company, Category.ELECTRICITY, "coupon_1", "coupon_1_desc", startDate, endDate, amount, price, imageUrl);
//			
//			customerService.purchaseCoupon(1L);
//			customerService.getCoupons();
//			customerService.getCustomerDetails();
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
