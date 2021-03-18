package rest_tamplet;

import java.sql.Date;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;

public class RestTampletTest {

	public static void main(String[] args) {

		try {
			Login login = new Login();
//			Admin admin = new Admin(login.adminLogin());
//			System.out.println("token: " + admin.getHeaders());
			// ****** Admin company methods *********
//			admin.getCompany(8L);
//			admin.getAllCompanies();
//			Company company = admin.getCompany(8L);
//			company.setEmail("123");
//			admin.updateCompany(company);
//			admin.addCompany(company);
//			admin.deleteCompany(9L);
//			company.setId(8L);
//			company.setEmail("rest_update");
//			admin.updateCompany(company);
			
			// ****** Admin customer methods *********
//			Customer customer = new Customer("rt1", "rt1", "rt1", "rt1");
//			admin.addCustomer(customer);
//			admin.getAllCustomers();
//			admin.getCustoemr(1L);
//			Customer customer = admin.getCustoemr(1L);
//			customer.setEmail("new_r");
//			admin.updateCustomer(customer);
//			admin.getCustoemr(1L);
//			admin.deleteCustomer(1L);
			
			// ****** Comapny methods ******** //
//			CompanyRest companyRest = new CompanyRest(login.companyLogin("1", "1"));	
//			System.out.println("token: " + companyRest.getHeaders());
//			Coupon coupon = new Coupon(Category.FOOD, "t_rt", "d_rt", new Date(2021, 1, 1), new Date(2021, 10, 2), 10, 50, "");
//			System.out.println(coupon.getCategoryId());
//			companyRest.addCoupon(coupon);
//			companyRest.getAllCoupons();
//			companyRest.getCompanyDetails();
			
			// ***** Customer methods ******** //
			CustomerRest customerRest = new CustomerRest(login.customerLogin("zzz", "zzz"));
//			customerRest.getCustomerDetails();
//			login.customerLogin("www", "www");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
