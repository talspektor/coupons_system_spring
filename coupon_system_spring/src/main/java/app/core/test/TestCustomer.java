package app.core.test;

import javax.transaction.Transactional;

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
	
	@Override
	@Transactional
	public void test(AdminService adminService, CompanyService companyService, CustomerService customerService) throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Customer");
		try {
			Company company = adminService.getAllCompanies().get(0);
			Customer customer = adminService.getAllCustomer().get(0);
			Coupon coupon = company.getCoupons().get(0);
						
//			customerService.purchaseCoupon(coupon.getId());
			System.out.println(customerService.getCoupons()); 
			System.out.println(customerService.getCustomerDetails());
			System.out.println(customerService.getCouponsByCategory(Category.ELECTRICITY));
			System.out.println(customerService.getCouponsByPriceLessThen(12));
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
