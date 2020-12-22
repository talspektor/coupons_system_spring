package app.core.repositories;

import org.springframework.data.repository.CrudRepository;

import app.core.entities.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Long> {

}
