package app.core.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	
	Optional<Customer> findByEmailAndPassword(String email, String password);
	
	boolean existsByEmail(String email);
	
	Optional<Customer> findByEmail(String email);
	
	@Query(value = "select c.coupon_id from Customer c where c.id = :id", nativeQuery = true)
	List<Coupon> findAllCouponsById(Long id);
	
	//TODO: 
//	@Query
//	boolean isCouponAlreadyPurchased(Long customerId, Long couponId);
	
	//TODO: Get all coupons
	
	//TODO: Get all coupons for category
	
	//TODO: isCouponAlredyPurchased
}
