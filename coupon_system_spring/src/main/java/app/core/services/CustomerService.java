package app.core.services;

import java.util.ArrayList;
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
public class CustomerService implements ClientService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository couponRepository;
	private Customer customer;
	
	public CustomerService() {
		super();
	}

	public long getId() {
		return customer.getId();
	}

	/**
	 * @param email - login email
	 * @param password - login password
	 * @return true if customer with given email and password is in database
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		System.out.println("Customer login");
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
			if (isCouponAlredyPurchased(couponId)) { return; }
			
			Optional<Coupon> optCoupon = couponRepository.findById(couponId);
			if (!optCoupon.isPresent()) { return; }
			Coupon coupon = optCoupon.get(); 
			
			if (!isCouponAvailable(coupon)) { return; }
			
			if (isCouponExpiered(coupon)) { return; }
			
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
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCoupons() {
		System.out.println("Customer getCoupons");
		return customer.getCoupons();
	}
	
	/**
	 * @param category
	 * @return all customer coupon for given category
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponsByCategory(Category category) {
		System.out.println("Customer getCouponsByCategory");
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : customer.getCoupons()) {
			if (coupon.getCategoryId() == category) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	/**
	 * @param maxPrice
	 * @return all customer coupons where price < maxPrice
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponsByPriceLessThen(double maxPrice) {
		System.out.println("Customer getCouponsByPriceLessThen");
		List<Coupon> coupons = new ArrayList<Coupon>();
		for (Coupon coupon : customer.getCoupons()) {
			if (coupon.getPrice() < maxPrice) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	/**
	 * @return customer by id
	 * @throws CouponSystemException
	 */
	public Customer getCustomerDetails() throws CouponSystemException {
		System.out.println("Customer getCustomerDetails()");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(getId());
			if(optCustomer.isPresent()) {
				return optCustomer.get();
			}
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getCustomerDetails fail :(", e);
		}
	}
	
	// ---------COUPON VALIDATION --------------
	
	/**
	 * @param couponId
	 * @return true if customer already purchased this coupon
	 */
	private boolean isCouponAlredyPurchased(Long couponId) {
		List<Coupon> coupons = customer.getCoupons();
		for (Coupon coupon : coupons) {
			if (couponId == coupon.getId()) {
				System.out.println("customer already purchase this coupon");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon amount > 0
	 */
	private boolean isCouponAvailable(Coupon coupon) {
		if(coupon.getAmount() > 0) {
			return true;
		}
		System.out.println("coupon is not available");
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon not expired
	 */
	private boolean isCouponExpiered(Coupon coupon) {
		if (coupon.getEndDate().compareTo(new Date()) > 0) {
			return false;
		}
		System.out.println("coupon is expiered");
		return true;
	}
}
