package app.core.services;

import java.util.Date;
import java.util.List;

import app.core.entities.Coupon;
import app.core.entities.Customer;

public class CouponValidator {

	private Customer customer;
	
	public CouponValidator(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * @param couponId
	 * @return true if customer already purchased this coupon
	 */
	public boolean isCouponAlredyPurchased(Long couponId) {
		List<Coupon> coupons = customer.getCoupons();
		if (coupons == null || coupons.isEmpty()) {
			return false;
		}
		for (Coupon coupon : coupons) {
			if (couponId == coupon.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon amount > 0
	 */
	public boolean isCouponAvailable(Coupon coupon) {
		if(coupon.getAmount() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon not expired
	 */
	public boolean isCouponExpiered(Coupon coupon) {
		if (coupon.getEndDate().compareTo(new Date()) > 0) {
			return false;
		}
		return true;
	}
}
