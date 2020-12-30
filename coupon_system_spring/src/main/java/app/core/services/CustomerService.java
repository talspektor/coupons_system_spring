package app.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
@Scope(value = "prototype")
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository couponRepository;
	private Customer customer;
	
	public long getId() {
		return customer.getId();
	}

	/**
	 * @param email - login email
	 * @param password - login password
	 * @return true if customer with given email and password is in database
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		try {
			Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
			if(optCustomer.isPresent()) {
				this.customer = optCustomer.get();
				System.out.println("login success :)");
				return true;
			}
			System.out.println("login fail :(");
			return false;
		} catch (Exception e) {
			throw new CouponSystemException("login fail :(", e); 
		}
	}
	
	public void purchaseCoupon(Long couponId) throws CouponSystemException {
		System.out.println("Customer purchaseCoupon");
		try {
			if (!isCouponNotPurchased(couponId)) { return; }
			
			Optional<Coupon> optCoupon = couponRepository.findById(couponId);
			if (!optCoupon.isPresent()) { return; }
			
			Coupon coupon = optCoupon.get(); 
			
			if (!isCouponAvailable(coupon)) { return; }
			
			if (!isCouponValid(coupon)) { return; }
			
			int amount = coupon.getAmount();
			coupon.setAmount(amount--);
			customer.addCoupn(coupon);
			customerRepository.save(customer);
			System.out.println("purchaseCoupon success :)");
		} catch (Exception e) {
			throw new CouponSystemException("purchaseCoupon fail :(", e); 
		}
	}
		
	/**
	 * @return all customer coupons
	 */
	public List<Coupon> getCoupons() throws CouponSystemException {
		System.out.println("Customer getCoupons");
		try {
			List<Coupon> coupons = customerRepository.findAllCouponsById(getId());
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getCoupons fail :(", e);
		}
	}
	
	public Customer getCustomerDetails() {
		System.out.println("Customer getCustomerDetails()");
		Optional<Customer> optCustomer = customerRepository.findById(getId());
		if(optCustomer.isPresent()) {
			return optCustomer.get();
		}
		// should not happen
		return null;
	}
	
	// TODO: implememnt method
	private boolean isCouponNotPurchased(Long couponId) {
		System.out.println("customer already purchase this coupon");
		
		System.out.println("coupon is already purchased");
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon amount > 0
	 */
	private boolean isCouponAvailable(Coupon coupon) {
		if(coupon.getAmount()>0) {
			return true;
		}
		System.out.println("coupon is not available");
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon not expired
	 */
	private boolean isCouponValid(Coupon coupon) {
		if (coupon.getEndDate().compareTo(new Date()) > 0) {
			return true;
		}
		System.out.println("coupon is expiered");
		return false;
	}
}
