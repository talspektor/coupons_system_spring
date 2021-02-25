package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;

@RestController
@RequestMapping("/api")
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	@Autowired
	private TokenValidator tokenValidator;
	
	@PostMapping("/add-coupon")
	public Coupon addCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		System.out.println("CompanyController addCoupon");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			Coupon addedCoupon = service.addCoupon(coupon);
			return addedCoupon;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	
	@PutMapping("/update-coupon")
	public Coupon updateCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		System.out.println("CompanyController updateCoupon");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			Coupon updatedCoupon = service.updateCoupon(coupon);
			return updatedCoupon;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}	
	}
	
	@DeleteMapping("/delete-coupon/{id}")
	public Coupon deleteCoupon(@PathVariable Long id, @RequestHeader String token) {
		System.out.println("CompanyController deleteCoupon");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			Coupon deletedCoupon = service.deleteCoupon(id);
			return deletedCoupon;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/company/coupons")
	public List<Coupon> getAllCounpanyCoupons(@RequestHeader String token) {
		System.out.println("CompanyController getAllCompanycouopons");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			List<Coupon> coupons = service.getCompanyCoupons();
			return coupons;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		} 
	}
	
	@GetMapping("/company/coupons/{maxPrice}")
	public List<Coupon> getCoumpanyCouponsPriceLessThen(@PathVariable double maxPrice, @RequestHeader String token) {
		System.out.println("CompanyController getCoumpanyCouponsPriceLessThen");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			List<Coupon> coupons = service.getCompanyCoupons(maxPrice);
			return coupons;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		} 
	}
	
	@GetMapping("/company")
	public Company getCompanyDetails(@RequestHeader String token) {
		System.out.println("CompanyController getCompanyDetails");
		if (tokenValidator.validate(token)) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		try {
			Company company = service.getCompanyDetails();
			return company; 
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
