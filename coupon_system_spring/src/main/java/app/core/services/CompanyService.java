package app.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.entities.Coupon;
import app.core.entities.Company;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
@Scope(value = "prototype")
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;
	
	private Long id;

	public Long getId() {
		return id;
	}
	
	/**
	 * @param email
	 * @param password
	 * @return true if company is in database
	 * @throws CouponSystemException
	 */
	public boolean login(String email, String password) throws CouponSystemException {
		System.out.println("Company login");
		try {
			if (!companyRepository.existsByEmailAndPassword(email, password)) {
				System.out.println("company not exists in database");
				return false;
			}
			return setId(email, password);
		} catch (Exception e) {
			throw new CouponSystemException("login fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param email
	 * @param password
	 * @return true if succeed saving the id
	 * @throws CouponSystemException
	 */
	private boolean setId(String email, String password) throws CouponSystemException {
		try {
			Optional<Company> optCompany = companyRepository.findByEmailAndPassword(email, password);
			if(optCompany.isPresent()) {
				this.id = optCompany.get().getId();
				System.out.println("login success :)");
				return true;
			}
		} catch (Exception e) {
			throw new CouponSystemException("setId fail :(" + e.getMessage(), e);
		}
		System.out.println("setId fail :(");
		return false;
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * add new coupon of the company to database
	 */
	public void addCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("Company addCoupon");
		try {
			if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), id)) {
				System.out.println("coupon is already in database.");
				return;
			}
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				optCompany.get().addCoupon(coupon);
				companyRepository.save(optCompany.get());
			}
		} catch (Exception e) {
			throw new CouponSystemException("addCoupon fail :(" + e.getMessage(), e);
		}
		
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * update coupon in database
	 */
	public void updateCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("Company updateCoupon");
		try {
			couponRepository.save(coupon);
		} catch (Exception e) {
			throw new CouponSystemException("updateCoupon fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * delete coupon from database
	 */
	public void deleteCoupon(Long couponId) throws CouponSystemException {
		System.out.println("Company deleteCoupon");
		try {
			//TODO: delete coupon purchase by coupon id
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				optCompany.get().removeCoupon(couponId);
				companyRepository.save(optCompany.get());
			}
		} catch (Exception e) {
			throw new CouponSystemException("deleteCoupon fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @return list of all company coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons() throws CouponSystemException{
		System.out.println("Company getCompanyCoupons");
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				return optCompany.get().getCoupons();
			}
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyCoupons fail :(" + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @param maxPrice
	 * @return list of company coupons where price less then maxPrice
	 * @throws CouponSystemException
	 */
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		System.out.println("Company getCompanyCoupons(maxPrice)");
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				//TODO: get coupons less then maxPrice
			}
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyCoupons(maxPrice) fail :(" + e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * @return the by the id company
	 * @throws CouponSystemException
	 */
	public Company getCompanyDetails() throws CouponSystemException {
		System.out.println("Company getCompanyDetails");
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				return optCompany.get();
			}
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyDetails(maxPrice) fail :(" + e.getMessage(), e);
		}
		return null;
	}
}
