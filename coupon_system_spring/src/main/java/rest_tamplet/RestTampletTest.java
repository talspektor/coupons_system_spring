package rest_tamplet;

import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.springframework.web.client.RestTemplate;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;

public class RestTampletTest {

	public static void main(String[] args) {

		try {
			Login login = new Login();
			Admin admin = new Admin(login.adminLogin().getToken());
//			System.out.println("token: " + admin.getHeaders());
			// ****** Admin company methods *********
//			admin.getCompany(8L);
//			admin.getAllCompanies();
//			Company company = admin.getCompany(8L);
//			company.setEmail("111");
//			admin.updateCompany(company);
//			Company company = new Company("z1", "z1", "z1");
//			admin.addCompany(company);
//			admin.deleteCompany(13L);
//			company.setId(8L);
//			company.setEmail("rest_update");
//			admin.updateCompany(company);
			
			// ****** Admin customer methods *********
//			Customer customer = new Customer("rt2", "rt2", "rt2", "rt2");
//			admin.addCustomer(customer);
//			admin.getAllCustomers();
			//Customer customer = admin.getCustoemr(2L);
//			Customer customer = admin.getCustoemr(1L);
			//dmin.updateCustomer(customer);
//			admin.getCustoemr(1L);
//			admin.deleteCustomer(2L);
			
			// ****** Comapny methods ******** //
			CompanyRest companyRest = new CompanyRest(login.companyLogin("1", "1").getToken());	
			System.out.println("token: " + companyRest.getHeaders());
			Coupon coupon = new Coupon(Category.FOOD, "t_rt", "d_rt", new Date(2021, 1, 1), new Date(2021, 10, 2), 10, 50, "");
			System.out.println(coupon.getCategory());
			companyRest.addCoupon(coupon);
//			companyRest.getAllCoupons();
//			companyRest.getCompanyDetails();
//			companyRest.getCouponsPriceLessThen(1);
			
			// ***** Customer methods ******** //
			CustomerRest customerRest = new CustomerRest(login.customerLogin("q", "q").getToken());
//			customerRest.getCustomerDetails();
//			customerRest.getAllCoupons();
//			customerRest.getCustomerCoupons();
//			customerRest.getCouponsByCategory(Category.FOOD);
//			customerRest.purchaseCoupon(24L);
//			login.customerLogin("www", "www");
			
			// using URI variables
//			{
//				String url = "https://localhots:8080/api/person?id={id}";
//				int id = Integer.parseInt(JOptionPane.showInputDialog("enter id"));
//				RestTemplate res = new RestTemplate();
//				res.getForObject(url, Prson.class, id);
//			}
			
//			{
//				String url = "https://localhots:8080/api/person?{params}={val}";
//				String param = "id";
//				int val = Integer.parseInt(JOptionPane.showInputDialog("enter id"));
//				RestTemplate res = new RestTemplate();
//				res.getForObject(url, Prson.class, param, val);
//			}
			
			// using URI variables in map
//			{
//				String url = "https://localhots:8080/api/person?{params}={val}";
//				String param = "id";
//				int val = Integer.parseInt(JOptionPane.showInputDialog("enter id"));
//				
//				Map<String, Object> mapOfVars = new HashMap<String, Object>();
//				mapOfVars.put("param", param);
//				mapOfVars.put("val", val);
//				
//				RestTemplate res = new RestTemplate();
//				res.getForObject(url, Prson.class, mapOfVars);
//			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
