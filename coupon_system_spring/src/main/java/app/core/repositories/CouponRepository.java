package app.core.repositories;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Category;
import app.core.entities.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
	
	List<Coupon> findAllByOrderByCategory();
	
	boolean existsByTitleAndCompanyId(String title, Long companyId);

	List<Coupon> findAllByCategory(Category category);
	@Transactional
	void removeByEndDateLessThan(Date endDate);
}
