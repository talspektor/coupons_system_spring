package app.core.controllers.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;
import app.core.session.Session;
import app.core.session.SessionContext;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private SessionContext sessionContext;

	@PutMapping("/purchase-coupon/{id}")
	public Coupon purchaseCoupon(@PathVariable Long id, @RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.purchaseCoupon(id);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons")
	public List<Coupon> getCostomerCoupons(@RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.getCoupons(); 
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/coupons")
	public List<Coupon> getAllCoupons(@RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.getAllDatabaseCoupons();
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons/category/{category}")
	public List<Coupon> getCouponsByCategory(@PathVariable Category category, @RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.getCouponsByCategory(category);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/coupons/maxPrice/{maxPrice}")
	public List<Coupon> getCouponsByPriceLessThen(@PathVariable double maxPrice, @RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.getCouponsByPriceLessThen(maxPrice);
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer")
	public Customer getCustomerDetails(@RequestHeader String token) {
		try {
			Session session = sessionContext.getSession(token);
			CustomerService service = (CustomerService) session.getAttritutes("service");
			return service.getCustomerDetails();
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
