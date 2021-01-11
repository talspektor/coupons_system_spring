package app.core.test;

import java.sql.Date;
import java.time.LocalDate;

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
		System.out.println("==================");
		System.out.println("Test Company");
		try {
			Company company = adminService.getAllCompanies().get(0);
			// Add coupon
			Date startDate = Date.valueOf(LocalDate.of(2020, 2, 1));
			Date endDate = Date.valueOf(LocalDate.of(2021, 2, 1));
			int amount = 5;
			double price = 10.5;
			String imageUrl = "some_url";
			Coupon coupon1 = new Coupon(company, Category.ELECTRICITY, "coupon_1", "coupon_1_desc", startDate, endDate, amount, price, imageUrl);
			companyService.addCoupon(coupon1);
			
			startDate = Date.valueOf(LocalDate.of(2018, 2, 1));
			endDate = Date.valueOf(LocalDate.of(2021, 2, 10));
			amount = 5;
			price = 10.5;
			imageUrl = "some_url";
			coupon1 = new Coupon(company, Category.FOOD, "coupon_2", "coupon_2_desc", startDate, endDate, amount, price, imageUrl);
			companyService.addCoupon(coupon1);
			
			// update coupon
			Coupon couponToUpdate = companyService.getCompanyCoupons().get(0);
			couponToUpdate.setAmount(20);
			companyService.updateCoupon(couponToUpdate);
			
			// get all company coupons
			System.out.println(companyService.getCompanyCoupons()); 
			
			// delete coupon
			Coupon couponToDelete = companyService.getCompanyCoupons().get(0);
			System.out.println(couponToDelete.getId());
			//TODO:
			companyService.deleteCoupon(couponToDelete.getId());

		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
