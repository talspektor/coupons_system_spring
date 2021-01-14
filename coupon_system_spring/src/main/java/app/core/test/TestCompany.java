package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.AdminService;
import app.core.services.CompanyService;

@Component
@Transactional
public class TestCompany {
	
	private AdminService adminService;
	
	private CompanyService companyService;
	@Autowired
	private LoginManager loginManager;

	public void login() throws CouponSystemException {
		adminService = (AdminService) loginManager.login("com.admin@admin", "admin", ClientType.ADMINISTRATOR);
		Company company = adminService.getAllCompanies().get(0);
		if(company == null) {
			System.out.println("*****  No companies in database!!! you can't continue test... ******");
			return;
		}
		companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPNY);
	}

	public void addCouponTest() throws CouponSystemException {
		System.out.println("============ Test add Coupon ==============");
		Company company = adminService.getCompany(companyService.getId());
		companyService.addCoupon(TestUtils.getRandomNewCoupon(company));
		System.out.println("=========================================");
	}

	public void updateCouponTest() throws CouponSystemException {
		System.out.println("============ Test update Coupon ==============");
		Coupon coupon = TestUtils.getRandomCouponFromDatabase(companyService);
		coupon.setAmount(TestUtils.getRandom());
		companyService.updateCoupon(coupon);
		System.out.println("=========================================");
	}
	
	public void getCompanyCouponsTest() throws CouponSystemException {
		System.out.println("============ Test get company coupons ==============");
		System.out.println(companyService.getCompanyCoupons());
		System.out.println("=========================================");
	}
	
	public void getCoumpanyCouponsByMaxPrice() throws CouponSystemException {
		System.out.println("============ Test get company coupons by max price ==============");
		System.out.println(companyService.getCompanyCoupons(TestUtils.getRandom()));
		System.out.println("=========================================");
	}
	
	public void deleteCouponTest() throws CouponSystemException {
		System.out.println("============ Test delete company coupon ==============");
		Coupon coupon = TestUtils.getRandomCouponFromDatabase(companyService);
		companyService.deleteCoupon(coupon.getId());
		System.out.println("=========================================");
	}
	
	public void getCompanyDetailsTest() throws CouponSystemException {
		System.out.println("============ Test get company details ==============");
		System.out.println(companyService.getCompanyDetails());
		System.out.println("=========================================");
	}
}
