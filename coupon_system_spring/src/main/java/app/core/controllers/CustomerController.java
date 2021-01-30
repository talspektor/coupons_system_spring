package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController implements ClientController {
	
	private CustomerService service;
	@Autowired
	private LoginManager loginManager;

	@Override
	@PostMapping("/customer/login/{email}/{password}")
	public boolean login(@PathVariable String email, @PathVariable String password) throws CouponSystemException {
		System.out.println("CustomerController login");
		service = (CustomerService)loginManager.login(email, password, ClientType.CUSTOMER);
		return service.login(email, password);
	}

	@PutMapping("/purchase-coupon/{id}")
	public void purchaseCoupon(@PathVariable Long id) throws CouponSystemException {
		System.out.println("CustomerController purchaseCoupon");
		service.purchaseCoupon(id);
	}
	
	@GetMapping("/customer/coupons")
	public List<Coupon> getCostomerCoupons() throws CouponSystemException {
		System.out.println("CustomerController getAllcoupons");
		return service.getCoupons();
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getAllCoupons() throws CouponSystemException {
		System.out.println("CustomerController getAllCoupons");
		return service.getAllDatabaseCoupons();
	}
	
	@GetMapping("/customer/coupons/category/")
	public List<Coupon> getCouponsByCategory(Category category) throws CouponSystemException {
		System.out.println("CustomerController getCouponsByCategory");
		return service.getCouponsByCategory(category);
	}
	
	@GetMapping("/customer/coupons/maxPrice/")
	public List<Coupon> getCouponsByPriceLessThen(double maxPrice) throws CouponSystemException {
		System.out.println("CustomerController getCouponsByPriceLessthen");
		return service.getCouponsByPriceLessThen(maxPrice);
	}
	
	@GetMapping("/customer")
	public Customer getCustomerDetails() throws CouponSystemException {
		System.out.println("CustomerController getCustomerDetails");
		return service.getCustomerDetails();
	}
}
