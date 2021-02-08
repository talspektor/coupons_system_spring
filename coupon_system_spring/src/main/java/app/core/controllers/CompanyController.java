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
	public ResponseEntity<ResponseItem<Boolean>> login(@PathVariable String email, @PathVariable String password) {
		System.out.println("CompanyController login");
		try {
			service = (CompanyService) loginManager.login(email, password, ClientType.ADMINISTRATOR);
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
	
	@PostMapping("/add-coupon")
	public void addCoupon(@RequestBody Coupon coupon) throws CouponSystemException {
		System.out.println("CompanyController addCoupon");
		service.addCoupon(coupon);
	}
	
	
	@PutMapping("/update-coupon")
	public void updateCoupon(@RequestBody Coupon coupon) throws CouponSystemException {
		System.out.println("CompanyController updateCoupon");
		service.updateCoupon(coupon);
	}
	
	@DeleteMapping("/delete-coupon/{id}")
	public void deleteCoupon(@PathVariable Long id) throws CouponSystemException {
		System.out.println("CompanyController deleteCoupon");
		service.deleteCoupon(id);
	}
	
	@GetMapping("/company/coupons")
	public List<Coupon> getAllCounpanyCoupons() throws CouponSystemException {
		System.out.println("CompanyController getAllCompanycouopons");
		return service.getCompanyCoupons();
	}
	
	@GetMapping("/company/coupons/{maxPrice}")
	public List<Coupon> getCoumpanyCouponsPriceLessThen(@PathVariable double maxPrice) throws CouponSystemException {
		System.out.println("CompanyController getCoumpanyCouponsPriceLessThen");
		return service.getCompanyCoupons(maxPrice);
	}
	
	@GetMapping("/company")
	public Company getCompanyDetails() throws CouponSystemException {
		System.out.println("CompanyController getCompanyDetails");
		return service.getCompanyDetails();
	}
}
