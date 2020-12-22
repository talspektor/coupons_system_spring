package app.core.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository couponRepository;
	private long id;
	
	public long getId() {
		return id;
	}

	/**
	 * @param email - login email
	 * @param password - login password
	 * @return true if customer with given email and password is in database
	 */
	public boolean login(String email, String password) {
		List<Long> ids = customerRepository.findIdByEmailAndPassword(email, password);
		if(ids != null && !ids.isEmpty()) {
			this.id = ids.get(0);
			System.out.println("login success :)");
			return true;
		}
		System.out.println("customer fail to login :(");
		return false;
	}
	
	//TODO: implement method
	public void purchaseCoupon(Long couponId) {
		
	}
	
	/**
	 * @return all customer coupons
	 */
	public List<Coupon> getCoupons() {
		System.out.println("Customer getCoupons");
		Optional<Customer> optCustomer = customerRepository.findById(id);
		if(optCustomer.isPresent()) {
			return optCustomer.get().getCoupons();
		}
		System.out.println("NO coupns for customer " + id);
		return null;
	}
	
	//TODO: implement method
	public List<Coupon> getCoupons(Category category) {
		System.out.println("Customer getCoupons(Category)");
		
		
	}
	
	//TODO: implement method
	public List<Coupon> getCoupons(double maxPrice) {
		
	}
	
	public Customer getCustomerDetails() {
		System.out.println("Customer getCustomerDetails()");
		Optional<Customer> optCustomer = customerRepository.findById(id);
		if(optCustomer.isPresent()) {
			return optCustomer.get();
		}
		// should not happen
		return null;
	}
	
	// TODO: implememnt method
	private boolean isCoupnNotPurchaseAlready(long couponId) {
//		System.out.println("customer already purchase this coupon");
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
		return false;
	}
}
