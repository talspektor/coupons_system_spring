package app.core.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.core.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	//TODO: return only the id
	Optional<Customer> findByEmailAndPassword(String email, String password);
	
	Optional<Customer> findByEmail(String email);
	
	//TODO: 
	@Query
	boolean isCouponAlreadyPurchased(Long customerId, Long couponId);
	
	//TODO: Get all coupons
	
	//TODO: Get all coupons for category
	
	//TODO: isCouponAlredyPurchased
}
