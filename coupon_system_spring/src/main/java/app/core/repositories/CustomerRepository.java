package app.core.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	
	Optional<Customer> findByEmailAndPassword(String email, String password);
	
	boolean existsByEmail(String email);
	
	boolean existsByPassword(String password);
	
	Optional<Customer> findByEmail(String email);
	
//	//TODO: check if there is a generic query
//	@Query(value = "select c.coupon_id from Customer c where c.tegury = :categury", nativeQuery = true)
//	List<Coupon> findAllCouponsByCategury(Category category);
	
	//TODO: check if there is a generic query
	@Query(value = "select c.coupon_id from Customer c where c.id = :id", nativeQuery = true)
	List<Coupon> findAllCouponsById(Long id);
	
	//TODO: check if there is a generic query
//	@Query(value = "select c.coupon_id from Customer c where c.id = :id", nativeQuery = true)
//	List<Coupon> findAllCouponsByPriceLessThen(double price);

}
