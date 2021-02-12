package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseItem<Boolean>> login(@PathVariable String email, @PathVariable String password) {
		System.out.println("CustomerController login");
		try {
			service = (CustomerService) loginManager.login(email, password, ClientType.CUSTOMER);
			if (service != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseItem<Boolean>(true));
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseItem<Boolean>(false));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Boolean>(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Boolean>(false, e.getMessage()));
		}
	}

	@PutMapping("/purchase-coupon/{id}")
	public ResponseEntity<ResponseItem<Coupon>> purchaseCoupon(@PathVariable Long id) {
		System.out.println("CustomerController purchaseCoupon");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Coupon>(service.purchaseCoupon(id)));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Coupon>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Coupon>(e.getMessage()));
		}
	}
	
	@GetMapping("/customer/coupons")
	public ResponseEntity<ResponseItem<List<Coupon>>> getCostomerCoupons() {
		System.out.println("CustomerController getAllcoupons");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(service.getCoupons())); 
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		}
	}
	
	@GetMapping("/coupons")
	public ResponseEntity<ResponseItem<List<Coupon>>> getAllCoupons() {
		System.out.println("CustomerController getAllCoupons");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(service.getAllDatabaseCoupons()));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		}
	}
	
	@GetMapping("/customer/coupons/category/{category}")
	public ResponseEntity<ResponseItem<List<Coupon>>> getCouponsByCategory(@PathVariable Category category) {
		System.out.println("CustomerController getCouponsByCategory");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(service.getCouponsByCategory(category)));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		}
	}
	
	@GetMapping("/customer/coupons/maxPrice/{maxPrice}")
	public ResponseEntity<ResponseItem<List<Coupon>>> getCouponsByPriceLessThen(@PathVariable double maxPrice) {
		System.out.println("CustomerController getCouponsByPriceLessthen");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(service.getCouponsByPriceLessThen(maxPrice)));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		}
	}
	
	@GetMapping("/customer")
	public ResponseEntity<ResponseItem<Customer>> getCustomerDetails() {
		System.out.println("CustomerController getCustomerDetails");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Customer>(service.getCustomerDetails()));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Customer>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Customer>(e.getMessage()));
		}
	}
}
