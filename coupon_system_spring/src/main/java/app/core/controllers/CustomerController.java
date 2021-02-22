package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public boolean login(@PathVariable String email, @PathVariable String password) {
		System.out.println("CustomerController login");
		try {
			service = (CustomerService) loginManager.login(email, password, ClientType.CUSTOMER);
			if (service != null) {
				return true;
			}
			throw new CouponSystemException(HttpStatus.BAD_REQUEST, "login fail :(");
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}

	@PutMapping("/purchase-coupon/{id}")
	public Coupon purchaseCoupon(@PathVariable Long id) {
		System.out.println("CustomerController purchaseCoupon");
		try {
			return service.purchaseCoupon(id);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons")
	public List<Coupon> getCostomerCoupons() {
		System.out.println("CustomerController getAllcoupons");
		try {
			return service.getCoupons(); 
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getAllCoupons() {
		System.out.println("CustomerController getAllCoupons");
		try {
			return service.getAllDatabaseCoupons();
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons/category/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable Category category) {
		System.out.println("CustomerController getCouponsByCategory");
		try {
			return service.getCouponsByCategory(category);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons/maxPrice/{maxPrice}")
	public List<Coupon> getCouponsByPriceLessThen(@PathVariable double maxPrice) {
		System.out.println("CustomerController getCouponsByPriceLessthen");
		try {
			return service.getCouponsByPriceLessThen(maxPrice);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer")
	public Customer getCustomerDetails() {
		System.out.println("CustomerController getCustomerDetails");
		try {
			return service.getCustomerDetails();
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
