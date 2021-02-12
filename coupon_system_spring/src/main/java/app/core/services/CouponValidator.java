package app.core.services;

import java.util.Date;
import java.util.List;

import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

public class CouponValidator {

	private Customer customer;
	
	public CouponValidator(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * @param couponId
	 * @return true if customer already purchased this coupon
	 */
	public boolean isCouponAlredyPurchased(Long couponId) throws CouponSystemException {
		List<Coupon> coupons = customer.getCoupons();
		if (coupons == null || coupons.isEmpty()) {
			return false;
		}
		for (Coupon coupon : coupons) {
			if (couponId == coupon.getId()) {
				throw new CouponSystemException("customer already purchase this coupon");
			}
		}
		return false;
	}
	
	/**
	 * @param coupon
	 * @return true if coupon amount > 0
	 */
	public boolean isCouponAvailable(Coupon coupon) throws CouponSystemException {
		if(coupon.getAmount() > 0) {
			return true;
		}
		throw new CouponSystemException("coupon is not available");
	}
	
	/**
	 * @param coupon
	 * @return true if coupon not expired
	 */
	public boolean isCouponExpiered(Coupon coupon) throws CouponSystemException {
		if (coupon.getEndDate().compareTo(new Date()) > 0) {
			return false;
		}
		throw new CouponSystemException("coupon is expiered");
	}
}
