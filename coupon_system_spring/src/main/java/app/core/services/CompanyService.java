package app.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.entities.Coupon;
import app.core.entities.Company;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
@Scope(value = "prototype")
public class CompanyService implements ClientService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CouponRepository couponRepository;
	
	public CompanyService() {
		super();
	}

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
		if (email == null || password == null) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "login fail :( email or password are null");
		}
		try {
			if (!companyRepository.existsByEmailAndPassword(email, password)) {
				throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "Wrong credentials - email: " + email + " password: " + password);
			}
			return setId(email, password);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "login fail :(" + e.getMessage(), e);
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
			throw new CouponSystemException(HttpStatus.BAD_REQUEST, "setId fail :(");
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "setId fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * add new coupon of the company to database - couopn's title most by unique
	 */
	public Coupon addCoupon(Coupon couponToAdd) throws CouponSystemException {
		System.out.println("Company addCoupon");
		if (couponToAdd == null) { 
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "couopn is null");
		}
		try {
			if (couponRepository.existsByTitleAndCompanyId(couponToAdd.getTitle(), id)) {
				throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "coupon is already in database.");
			}
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) { 
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "addCoupon fail can't find coupon in database");
			}
			List<Coupon> coupons = optCompany.get().getCoupons();
			if (coupons == null) {
				coupons = new ArrayList<Coupon>();
			} else {
				for (Coupon coupon : coupons) {
					if(coupon.getTitle() == couponToAdd.getTitle()) {
						throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "addCoupon fail: can't add coupon's title most by unique");
					}
				}
			}
			couponToAdd.setCompany(optCompany.get());
			optCompany.get().addCoupon(couponToAdd);
			companyRepository.save(optCompany.get());
			
			Coupon addedCoupon = couponRepository.findByTitle(couponToAdd.getTitle());
			return addedCoupon;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "addCoupon fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param coupon
	 * @throws CouponSystemException
	 * update coupon in database
	 */
	public Coupon updateCoupon(Coupon coupon) throws CouponSystemException {
		System.out.println("Company updateCoupon");
		if (coupon == null) {
			throw new CouponSystemException(HttpStatus.UNAUTHORIZED, "coupon is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) { 
				throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "updateCoupon fail");
			}
			Optional<Coupon> optCoupon = couponRepository.findById(coupon.getId());
			if (!optCoupon.isPresent()) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not in database");
			}
			optCoupon.get().setAmount(coupon.getAmount());
			optCoupon.get().setCategory(coupon.getCategoryId());
			optCoupon.get().setDescription(coupon.getDescription());
			optCoupon.get().setEndDate(coupon.getEndDate());
			optCoupon.get().setImageUrl(coupon.getImageUrl());
			optCoupon.get().setPrice(coupon.getPrice());
			optCoupon.get().setStartDate(coupon.getStartDate());
			optCoupon.get().setTitle(coupon.getTitle());
			couponRepository.save(optCoupon.get());
			return optCoupon.get();
		} catch (Exception e) {
			throw new CouponSystemException("updateCoupon fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param couponId
	 * @throws CouponSystemException
	 * delete coupon from database
	 */
	public Coupon deleteCoupon(Long couponId) throws CouponSystemException {
		System.out.println("Company deleteCoupon");
		if (couponId == null) {
			throw new CouponSystemException("couponId is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				
				Coupon coupon = optCompany.get().removeCoupon(couponId);
				companyRepository.save(optCompany.get());
				couponRepository.deleteById(couponId);
				System.out.println("success");
				return coupon;
			}
			throw new CouponSystemException("deleteCoupon fail");
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
			if (!optCompany.isPresent()) {
				return new ArrayList<Coupon>();
			}
			return optCompany.get().getCoupons();
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyCoupons fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param maxPrice
	 * @return list of company coupons where price less then maxPrice
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		System.out.println("Company getCompanyCoupons(maxPrice)");
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) {
				throw new CouponSystemException("companyRepository.findById(id) fail :(");
			}
			List<Coupon> coupons = optCompany.get().getCoupons();
			List<Coupon> couponsToReturn = new ArrayList<Coupon>();
			for (Coupon coupon : coupons) {
				if (coupon.getPrice() < maxPrice) {
					couponsToReturn.add(coupon);
				}
			}
			return couponsToReturn;
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyCoupons(maxPrice) fail :(" + e.getMessage(), e);
		}
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
