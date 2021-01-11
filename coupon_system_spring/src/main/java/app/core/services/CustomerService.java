package app.core.services;

import java.util.ArrayList;
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
		if (couponId == null) { return; }  
		System.out.println("Customer purchaseCoupon");
		try {
			CouponValidator validator = new CouponValidator(customer);
			if (validator.isCouponAlredyPurchased(couponId)) { return; }
			
			Coupon coupon = getCoupon(couponId);
			if (coupon == null) { return; }
			
			if (!validator.isCouponAvailable(coupon)) { return; }
			if (validator.isCouponExpiered(coupon)) { return; }
			
			addCoupon(coupon);
			System.out.println("purchaseCoupon success :)");
		} catch (Exception e) {
			throw new CouponSystemException("purchaseCoupon fail :(", e); 
		}
	}
		
	/**
	 * @return all customer coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCoupons() throws CouponSystemException {
		System.out.println("Customer getCoupons");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customer.getId());
			if (optCustomer.isPresent()) {
				return optCustomer.get().getCoupons();
			}
			System.out.println("no coupons for this customer");
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getCoupons fail :(", e); 
		}
	}
	
	/**
	 * @param category
	 * @return all customer coupon for given category
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCouponsByCategory(Category category) throws CouponSystemException {
		System.out.println("Customer getCouponsByCategory");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customer.getId());
			if (!optCustomer.isPresent()) {
				return null;
			}
			List<Coupon> coupons = new ArrayList<Coupon>();
			for (Coupon coupon : optCustomer.get().getCoupons()) {
				if (coupon.getCategoryId() == category) {
					coupons.add(coupon);
				}
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getCouponsByCategory fail :(", e); 
		}
	}
	
	/**
	 * @param maxPrice
	 * @return all customer coupons where price < maxPrice
	 * @throws CouponSystemException
	 */
	//TODO: ?
	public List<Coupon> getCouponsByPriceLessThen(double maxPrice) throws CouponSystemException {
		System.out.println("Customer getCouponsByPriceLessThen");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customer.getId());
			if (!optCustomer.isPresent()) {
				return null;
			}
			List<Coupon> coupons = new ArrayList<Coupon>();
			for (Coupon coupon : optCustomer.get().getCoupons()) {
				if (coupon.getPrice() < maxPrice) {
					coupons.add(coupon);
				}
			}
			return coupons;
		} catch (Exception e) {
			throw new CouponSystemException("getCouponsByPriceLessThen fail :(", e);
		}
		
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
	
	/**
	 * @param couponId
	 * @return coupon
	 */
	private Coupon getCoupon(Long couponId) throws CouponSystemException {
		try {
			Optional<Coupon> optCoupon = couponRepository.findById(couponId);
			if (!optCoupon.isPresent()) { return null; }
			return optCoupon.get();
		} catch (Exception e) {
			throw new CouponSystemException("getCoupon fail :(", e);
		}
		
	}
	
	/** add coupon to customer coupon list
	 * @param coupon
	 */
	private void addCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Optional<Customer> optCustomer = customerRepository.findById(getId());
			if (!optCustomer.isPresent()) { return; }
			int amount = coupon.getAmount();
			coupon.setAmount(amount--);
			optCustomer.get().addCoupn(coupon);
			customerRepository.save(optCustomer.get());
		} catch (Exception e) {
			throw new CouponSystemException("addCoupon fail :(", e);
		}
	}
}
