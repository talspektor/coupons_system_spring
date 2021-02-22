package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Boolean>(false, e.getMessage()));
		}
		
	}
	
	@PostMapping("/add-coupon")
	public ResponseEntity<ResponseItem<Coupon>> addCoupon(@RequestBody Coupon coupon) {
		System.out.println("CompanyController addCoupon");
		try {
			Coupon addedCoupon = service.addCoupon(coupon);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Coupon>(addedCoupon));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Coupon>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Coupon>(e.getMessage()));
		}
		
	}
	
	
	@PutMapping("/update-coupon")
	public ResponseEntity<ResponseItem<Coupon>> updateCoupon(@RequestBody Coupon coupon) {
		System.out.println("CompanyController updateCoupon");
		try {
			Coupon updatedCoupon = service.updateCoupon(coupon);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Coupon>(updatedCoupon));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Coupon>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Coupon>(e.getMessage()));
		}
		
	}
	
	@DeleteMapping("/delete-coupon/{id}")
	public ResponseEntity<ResponseItem<Coupon>> deleteCoupon(@PathVariable Long id) {
		System.out.println("CompanyController deleteCoupon");
		try {
			Coupon deletedCoupon = service.deleteCoupon(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Coupon>(deletedCoupon));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Coupon>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Coupon>(e.getMessage()));
		}
		
	}
	
	@GetMapping("/company/coupons")
	public ResponseEntity<ResponseItem<List<Coupon>>> getAllCounpanyCoupons() {
		System.out.println("CompanyController getAllCompanycouopons");
		try {
			List<Coupon> coupons = service.getCompanyCoupons();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(coupons));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} 
	}
	
	@GetMapping("/company/coupons/{maxPrice}")
	public ResponseEntity<ResponseItem<List<Coupon>>> getCoumpanyCouponsPriceLessThen(@PathVariable double maxPrice) {
		System.out.println("CompanyController getCoumpanyCouponsPriceLessThen");
		try {
			List<Coupon> coupons = service.getCompanyCoupons(maxPrice);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Coupon>>(coupons));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Coupon>>(e.getMessage()));
		} 
	}
	
	@GetMapping("/company")
	public ResponseEntity<ResponseItem<Company>> getCompanyDetails() {
		System.out.println("CompanyController getCompanyDetails");
		try {
			Company company = service.getCompanyDetails();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(company)); 
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
	}
}
