package app.core.test;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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
public class TestAdmin implements TestClient {

	@Override
	public void test(AdminService adminService, CompanyService companyService, CustomerService customerService) throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Admin");
		try {
			// add company
//			Company company = new Company("company_2", "company_2@mail.com", "pass_1");
//			adminService.addCompany(company);
			
//			company = adminService.getAllCompanies().get(0);
//			System.out.println(adminService.getCompany(company.getId()));
//			System.out.println(adminService.getAllCompanies());
			
			// add customer
//			Customer customer = new Customer("customer_name_1", "customer_lastName_1", "customer_1@email.com", "pass_1");
//			adminService.addCustomer(customer);
			
//			customer = adminService.getAllCustomer().get(0);
//			System.out.println(adminService.getCustomer(customer.getId()));
//			System.out.println(adminService.getAllCustomer());
			
			// delete company with coupon
//			Company company = new Company("123", "123.com", "123");
//			// Add coupon
//			Date startDate = Date.valueOf(LocalDate.of(2020, 2, 1));
//			Date endDate = Date.valueOf(LocalDate.of(2021, 2, 1));
//			int amount = 5;
//			double price = 10.5;
//			String imageUrl = "some_url";
//			Coupon coupon = new Coupon(company, Category.ELECTRICITY, "coupon_1", "coupon_1_desc", startDate, endDate,
//					amount, price, imageUrl);
//			company.addCoupon(coupon);
//			adminService.addCompany(company);
//			List<Company> companies = adminService.getAllCompanies();
//			company = companies.get(companies.size()-1);
			
//			adminService.deleteCoumpany(company.getId());
			
			// delete customer
			// add customer
			Customer customerToDelete = new Customer("customer_name_2", "customer_lastName_2", "customer_2@email.com", "pass_2");
			Coupon coupon = adminService.getAllCompanies().get(0).getCoupons().get(0);
			customerToDelete.addCoupn(coupon);
			adminService.addCustomer(customerToDelete);
//			
//			List<Customer> customers = adminService.getAllCustomer();
//			customerToDelete = customers.get(customers.size()-1);
//			adminService.deleteCustomer(customer.getId());
		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
		
	}
}
