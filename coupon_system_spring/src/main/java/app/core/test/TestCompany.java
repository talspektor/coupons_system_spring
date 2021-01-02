package app.core.test;

import java.sql.Date;

import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class TestCompany implements TestClient {

	@Override
	public void test(AdminService adminService, CompanyService companyService, CustomerService customerService) throws CouponSystemException {
		try {
			Company company = adminService.getAllCompanies().get(0);
			
			Date startDate = new Date(2018, 1, 10);
			Date endDate = new Date(2021, 1, 10);
			int amount = 5;
			double price = 10.5;
			String imageUrl = "some_url";
			Coupon coupon1 = new Coupon(company, Category.ELECTRICITY, "coupon_1", "coupon_1_desc", startDate, endDate, amount, price, imageUrl);
			companyService.addCoupon(coupon1);
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
