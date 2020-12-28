package app.core.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {

	List<Coupon> findAllByAndCategory(Category category);
	
//	List<Coupon> findAllByPriceLessThen(double price);
}
