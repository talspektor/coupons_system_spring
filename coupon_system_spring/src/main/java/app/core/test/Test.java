package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.entities.Category;
import app.core.exceptions.CouponSystemException;

@Component
public class Test {

	@Autowired
	private ApplicationContext context;

	@Transactional
	public void testAll() throws CouponSystemException {
		
		{
			// ADMINISTRATOR
//			TestAdmin testAdmin = context.getBean(TestAdmin.class);
//			testAdmin.loginTest();
//			testAdmin.addCompanyTest();
//			testAdmin.getCompanyTest();
//			testAdmin.getAllCompaniesTest();
//			testAdmin.addCustomerTest();
//			testAdmin.getCustomerTest();
//			testAdmin.getAllCustomersTest();
//			testAdmin.deleteCompanyTest();
//			testAdmin.deleteCustomerTest();
		}
		
		{
			// COMPANY
//			TestCompany testCompany = context.getBean(TestCompany.class);
//			testCompany.login();
//			testCompany.addCouponTest();
//			testCompany.updateCouponTest();
//			testCompany.getCompanyCouponsTest();
//			testCompany.getCoumpanyCouponsByMaxPrice();
//			testCompany.deleteCouponTest();
//			testCompany.getCompanyDetailsTest();
		}
		
		{
			// CUSTOMER
//			TestCustomer testCustomer = context.getBean(TestCustomer.class);
//			testCustomer.login();
//			testCustomer.purchaseCouponTest();
//			testCustomer.getCouponsTest();
//			testCustomer.getAllCouponsFromDB();
//			testCustomer.getCouponsByCategoryTest(Category.ELECTRICITY);
//			testCustomer.getCouponsByPriceLessThenTest();
//			testCustomer.getCustomerDetailsTest();
		}	
	}
}
