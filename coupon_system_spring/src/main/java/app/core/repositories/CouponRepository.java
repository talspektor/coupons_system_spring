package app.core.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
	
	boolean existsByTitleAndCompanyId(String title, Long companyId);

	List<Coupon> findAllByCategory(Category category);
	
	//TODO: check if there is a generic query
	@Query(value = "delete coupon where coupon.endDate<:endDate", nativeQuery = true)
	void removeByEndDateLessThen(Date endDate);
}
