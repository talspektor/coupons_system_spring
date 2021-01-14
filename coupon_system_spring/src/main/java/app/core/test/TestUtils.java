package app.core.test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;

public class TestUtils {
	
	static Company getRandomCompanyFromDatabase(AdminService adminService) throws CouponSystemException {
		List<Company> companies = adminService.getAllCompanies();
		int size = companies.size();
		return companies.get((int)(Math.random() + size -1));
	}
	
	static Customer getRandomCustomerFromDatabase(AdminService adminService) throws CouponSystemException {
		List<Customer> customers = adminService.getAllCustomer();
		int size = customers.size();
		return customers.get((int)(Math.random() + size -1));
	}
	
	static Coupon getRandomCouponFromDatabase(CompanyService service) throws CouponSystemException {
		List<Coupon> coupons = service.getCompanyCoupons();
		int size = coupons.size();
		return coupons.get((int)(Math.random() + size -1));
	}

	static int getRandom() {
		return (int)(Math.random() * 1000);
	}
	
	static Company getRandomNewCompany() {
		return new Company("companyName_" + getRandom(), "email" + getRandom() +"@email.com", "pass" + getRandom());
	}
	
	static Customer getRandomNewCustomer() {
		return new Customer("customerName_" + getRandom(), "lastName_" + getRandom(), getRandom() + "@email.com", "pass" + getRandom());
	}
	
	static Category getRandomCategory() {
		Category[] categories = {Category.ELECTRICITY, Category.FOOD, Category.SPORTS, Category.VACATION};
		return categories[(int)(Math.random() * 4)];
	}
	
	static Coupon getRandomNewCoupon(Company company) {
		String title = "title_" + getRandom();
		String description = "desc_" + getRandom();
		Date startDate = Date.valueOf(LocalDate.of(2020, 2, 1));
		Date endDate = Date.valueOf(LocalDate.of(2021, 2, 1));
		int amount = (int)(Math.random() * 25);
		double price = (double)(Math.random() * 1000);
		String imageUrl = "some_url";
		Category category = getRandomCategory();
		
		return new Coupon(company, category, title, description, startDate, endDate, amount, price, imageUrl);
	}
}
