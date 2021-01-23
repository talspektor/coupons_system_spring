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
	private Long id;
	
	public CustomerService() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	/**
	 * @param email - login email
	 * @param password - login password
	 * @return true if customer with given email and password is in database
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		System.out.println("Customer login");
		if (email == null || password == null) { 
			throw new CouponSystemException("login fail :( email or password are null");
		}
		try {
			Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
			if(optCustomer.isPresent()) {
				this.id = optCustomer.get().getId();
				System.out.println("login success :)");
				return true;
			}
			System.out.println("login fail :( costomer is not in database");
			return false;
		} catch (Exception e) {
			throw new CouponSystemException("login fail :(", e); 
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * add coupon to customer coupons
	 */
	public void purchaseCoupon(Long couponId) throws CouponSystemException {
		System.out.println("Customer purchaseCoupon");
		if (couponId == null) {
			System.out.println("couponId is null...");
			return;
		} 
		try {
			Optional<Customer> optCustomer = customerRepository.findById(id);
			if (!optCustomer.isPresent()) { return; }
			
			CouponValidator validator = new CouponValidator(optCustomer.get());
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
			Optional<Customer> optCustomer = customerRepository.findById(id);
			if (optCustomer.isPresent()) {
				return optCustomer.get().getCoupons();
			}
			System.out.println("no coupons for this customer :(");
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getCoupons fail :(", e); 
		}
	}
	
	/**
	 * @return All the coupons from database
	 * @throws CouponSystemException
	 */
	public List<Coupon> getAllDatabaseCoupons() throws CouponSystemException {
		System.out.println("Customer getAllDatabaseCoupons");
		try {
			return (List<Coupon>) couponRepository.findAllByOrderByCategory();
		} catch (Exception e) {
			throw new CouponSystemException("getAllDatabaseCoupons fail :(", e); 
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
			Optional<Customer> optCustomer = customerRepository.findById(id);
			if (!optCustomer.isPresent()) {
				throw new CouponSystemException("customerRepository.findById(id=" + id + ") fail :(");
			}
			List<Coupon> coupons = new ArrayList<Coupon>();
			for (Coupon coupon : optCustomer.get().getCoupons()) {
				if (coupon.getCategoryId() == category) {
					System.out.println(coupon.getCategoryId() + "   " + category);
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
	public List<Coupon> getCouponsByPriceLessThen(double maxPrice) throws CouponSystemException {
		System.out.println("Customer getCouponsByPriceLessThen");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(id);
			if (!optCustomer.isPresent()) {
				throw new CouponSystemException("customerRepository.findById(id=" + id + ") fail :(");
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
			Optional<Customer> optCustomer = customerRepository.findById(id);
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
		if (couponId == null) { 
			throw new CouponSystemException("couponId is null :(");
		}
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
		if (coupon == null) { 
			throw new CouponSystemException("Coupon is null :(");
		}
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
