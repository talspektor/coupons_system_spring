package app.core.controllers.clients;

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
import app.core.session.Session;
import app.core.session.SessionContext;

@RestController
@RequestMapping("/api")
public class CompanyController {
	
	@Autowired
	private SessionContext sessionContext;
	
	@PostMapping("/add-coupon")
	public Coupon addCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		System.out.println("CompanyController addCoupon");
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
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
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
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
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
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
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
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
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
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
		try {
			Session session = sessionContext.getSession(token);
			CompanyService service = (CompanyService) session.getAttritutes("service");
			return service.getCompanyDetails(); 
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
