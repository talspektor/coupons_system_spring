package app.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE,"login fail :( email or password are null");
		}
		try {
			Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
			if(optCustomer.isPresent()) {
				this.id = optCustomer.get().getId();
				System.out.println("login success :)");
				return true;
			}
			throw new CouponSystemException(HttpStatus.NOT_FOUND, "Wrong credentials - email: " + email + " password: " + password);
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "login fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "login fail :(", e); 
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * add coupon to customer coupons
	 */
	public Coupon purchaseCoupon(Long couponId) throws CouponSystemException {
		System.out.println("Customer purchaseCoupon");
		if (couponId == null) { 
			throw new CouponSystemException(HttpStatus.NOT_FOUND, "couponId is null :(");
		} 
		try {
			Optional<Customer> optCustomer = customerRepository.findById(id);
			if (!optCustomer.isPresent()) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not in database");
			}
			
			CouponValidator validator = new CouponValidator(optCustomer.get());
			if (validator.isCouponAlredyPurchased(couponId)) { 
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "Coupon is alredy purchased");
			}
			
			Coupon coupon = getCoupon(couponId);
			if (coupon == null) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not in database");
			}
			
			if (!validator.isCouponAvailable(coupon)) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not available");
			}
			
			if (validator.isCouponExpiered(coupon)) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon expiered");
			}
			
			addCoupon(coupon);
			System.out.println("purchaseCoupon success :)");
			return coupon;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "purchaseCoupon fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "purchaseCoupon fail :(" + e.getMessage(), e); 
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
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCoupons fail :(");
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCoupons fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getCoupons fail :(", e); 
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
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getAllDatabaseCoupons fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getAllDatabaseCoupons fail :(", e); 
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
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCouponsByCategory fail :(");
			}
			List<Coupon> coupons = new ArrayList<Coupon>();
			for (Coupon coupon : optCustomer.get().getCoupons()) {
				if (coupon.getCategoryId() == category) {
					System.out.println(coupon.getCategoryId() + "   " + category);
					coupons.add(coupon);
				}
			}
			return coupons;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCouponsByCategory fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getCouponsByCategory fail :(", e); 
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
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCouponsByPriceLessThen fail :(");
			}
			List<Coupon> coupons = new ArrayList<Coupon>();
			for (Coupon coupon : optCustomer.get().getCoupons()) {
				if (coupon.getPrice() < maxPrice) {
					coupons.add(coupon);
				}
			}
			return coupons;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCouponsByPriceLessThen fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getCouponsByPriceLessThen fail :(", e);
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
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCustomerDetails fail");
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCustomerDetails fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getCustomerDetails fail :(", e);
		}
	}
	
	/**
	 * @param couponId
	 * @return coupon
	 */
	private Coupon getCoupon(Long couponId) throws CouponSystemException {
		if (couponId == null) { 
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "couponId is null :(");
		}
		try {
			Optional<Coupon> optCoupon = couponRepository.findById(couponId);
			if (!optCoupon.isPresent()) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not in database");
			}
			return optCoupon.get();
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCoupon fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "getCoupon fail :(", e);
		}
		
	}
	
	/** add coupon to customer coupon list
	 * @param coupon
	 */
	private void addCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon == null) { 
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "Coupon is null :(");
		}
		try {
			Optional<Customer> optCustomer = customerRepository.findById(getId());
			if (!optCustomer.isPresent()) {
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "addCoupon fail :(");
			}
			
			int amount = coupon.getAmount();
			coupon.setAmount(amount--);
			optCustomer.get().addCoupn(coupon);

		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCoupon fail :(" + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "addCoupon fail :(", e);
		}
	}
}
