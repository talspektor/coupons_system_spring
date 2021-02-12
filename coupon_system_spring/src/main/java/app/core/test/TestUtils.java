package app.core.test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

public class TestUtils {
	
	static Company getRandomCompanyFromDatabase(AdminService adminService) throws CouponSystemException {
		List<Company> companies = adminService.getAllCompanies();
		if (companies.isEmpty()) {
			throw new CouponSystemException("Test Utils - there are no companies in database");
		}
		int size = companies.size();
		int index = (int)(Math.random() * (size -1));
		return companies.get(index);
	}
	
	static Customer getRandomCustomerFromDatabase(AdminService adminService) throws CouponSystemException {
		List<Customer> customers = adminService.getAllCustomer();
		if (customers.isEmpty()) {
			throw new CouponSystemException("Test Utils - there are no customers in database");
		}
		int size = customers.size();
		int index = (int)(Math.random() * (size -1));
		System.out.println(">>>>>" + index);
		return customers.get(index);
	}
	
	static Coupon getRandomCouponFromDatabase(AdminService service) throws CouponSystemException {
		List<Company> companies = service.getAllCompanies();
		if (companies==null || companies.isEmpty()) {
			throw new CouponSystemException("Test Utils - there are no companies in database");
		}
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Company company : companies) {
			List<Coupon> companyCoupons = company.getCoupons();
			if (companyCoupons != null && !companyCoupons.isEmpty()) {
				coupons = Stream.concat(coupons.stream(), companyCoupons.stream())
	                    .collect(Collectors.toList());
			}
		}
		if (coupons == null) {
			throw new CouponSystemException("Test Utils - there are no coupons in database");
		}
		int size = coupons.size();
		int index = (int)(Math.random() * (size -1));
		return coupons.get(index);
	}

	static int getRandom() {
		int random = (int)(Math.random() * 1000);
		return random;
	}
	
	static int getRandom(int upto) {
		int random = (int)(Math.random() * upto);
		return random;
	}
	
	static Company getRandomNewCompany() {
		return new Company("companyName_" + getRandom(), "email" + getRandom() +"@email.com", "pass" + getRandom());
	}
	
	static Customer getRandomNewCustomer() {
		return new Customer("customerName_" + getRandom(), "lastName_" + getRandom(), getRandom() + "@email.com", "pass" + getRandom());
	}
	
	static Category getRandomCategory() {
		Category[] categories = {Category.ELECTRICITY, Category.FOOD, Category.SPORTS, Category.VACATION};
		return categories[getRandom(3)];
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
		
		return new Coupon(category, title, description, startDate, endDate, amount, price, imageUrl);
	}
}
