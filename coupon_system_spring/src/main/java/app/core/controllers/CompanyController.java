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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.CompanyService;

@RestController
@RequestMapping("/api")
public class CompanyController implements ClientController {
	
	private CompanyService service;
	@Autowired
	private LoginManager loginManager;
	

	@Override
	@PostMapping("/compamy/login/{email}/{password}")
	public boolean login(@PathVariable String email, @PathVariable String password) {
		System.out.println("CompanyController login");
		try {
			service = (CompanyService) loginManager.login(email, password, ClientType.COMPNY);
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
	
	@PostMapping("/add-coupon")
	public Coupon addCoupon(@RequestBody Coupon coupon) {
		System.out.println("CompanyController addCoupon");
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
	public Coupon updateCoupon(@RequestBody Coupon coupon) {
		System.out.println("CompanyController updateCoupon");
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
	public Coupon deleteCoupon(@PathVariable Long id) {
		System.out.println("CompanyController deleteCoupon");
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
	public List<Coupon> getAllCounpanyCoupons() {
		System.out.println("CompanyController getAllCompanycouopons");
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
	public List<Coupon> getCoumpanyCouponsPriceLessThen(@PathVariable double maxPrice) {
		System.out.println("CompanyController getCoumpanyCouponsPriceLessThen");
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
	public Company getCompanyDetails() {
		System.out.println("CompanyController getCompanyDetails");
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
