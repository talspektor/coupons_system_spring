package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
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
		Optional<Customer> optCustomer = customerRepository.findByEmailAndPassword(email, password);
		if(optCustomer.isPresent()) {
			this.id = optCustomer.get().getId();
			System.out.println("login success :)");
			return true;
		}
		System.out.println("customer fail to login :(");
		return false;
	}
	
	//TODO: 
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
	
	public List<Coupon> getCoupons(Category category) {
		System.out.println("Customer getCoupons(Category)");
		
	}
}
