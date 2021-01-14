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
public class TestCompany implements TestClient {
	
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
		companyService.addCoupon(Test.getRandomNewCoupon(company));
		System.out.println("=========================================");
	}
	
	public void updateCouponTest() throws CouponSystemException {
		System.out.println("============ Test update Coupon ==============");
		Coupon couponToUpdate = companyService.getCompanyCoupons().get(0);
		couponToUpdate.setAmount(Test.getRandom());
		companyService.updateCoupon(couponToUpdate);
		System.out.println("=========================================");
	}
	
	public void getCompanyCouponsTest() throws CouponSystemException {
		System.out.println("============ Test get company coupons ==============");
		System.out.println(companyService.getCompanyCoupons());
		System.out.println("=========================================");
	}
	
	public void getCoumpanyCouponsByMaxPrice() throws CouponSystemException {
		System.out.println("============ Test get company coupons by max price ==============");
		System.out.println(companyService.getCompanyCoupons(Test.getRandom()));
		System.out.println("=========================================");
	}
	
	public void deleteCouponTest() throws CouponSystemException {
		System.out.println("============ Test delete company coupon ==============");
		Coupon couponToDelete = companyService.getCompanyCoupons().get(0);
		companyService.deleteCoupon(couponToDelete.getId());
		System.out.println("=========================================");
	}
	
	public void getCompanyDetailsTest() throws CouponSystemException {
		System.out.println("============ Test get company details ==============");
		System.out.println(companyService.getCompanyDetails());
		System.out.println("=========================================");
	}

	@Override
	public void test() throws CouponSystemException {
		System.out.println("==================");
		System.out.println("Test Company");
		try {
			addCouponTest();
			
			updateCouponTest();
			
			getCompanyCouponsTest();
			
			getCoumpanyCouponsByMaxPrice();
			
			deleteCouponTest();
			
			getCompanyDetailsTest();

		} catch (Exception e) {
			throw new CouponSystemException("test " + e.getMessage(), e);
		}
	}
}
