package app.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
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
		if (email == null || password == null) {
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "login fail :( email or password are null");
		}
		try {
			if (!companyRepository.existsByEmailAndPassword(email, password)) {
				throw new CouponSystemException(HttpStatus.NOT_FOUND,
						"Wrong credentials - email: " + email + " password: " + password);
			}
			return setId(email, password);
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "login fail :(" + e.getMessage(), e);
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
			if (optCompany.isPresent()) {
				this.id = optCompany.get().getId();
				System.out.println("login success :)");
				return true;
			}
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "setId fail to fide company ");
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "setId fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "setId fail :(" + e.getMessage(), e);
		}
	}

	/**
	 * @param coupon
	 * @throws CouponSystemException add new coupon of the company to database -
	 *                               couopn's title most by unique
	 */
	public Coupon addCoupon(Coupon couponToAdd) throws CouponSystemException {
		if (couponToAdd == null) {
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "couopn is null");
		}
		try {
			if (couponRepository.existsByTitleAndCompanyId(couponToAdd.getTitle(), id)) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is already in database.");
			}
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) {
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE,
						"addCoupon fail can't find coupon in database");
			}
			if (!isCouponTitleUnique(couponToAdd, optCompany.get())) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST,
						"addCoupon fail: can't add coupon's title most by unique");
			}
			couponToAdd.setCompany(optCompany.get());
			couponToAdd = couponRepository.save(couponToAdd);
			optCompany.get().addCoupon(couponToAdd);

			return couponToAdd;
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "addCoupon fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "addCoupon fail :(" + e.getMessage(), e);
		}
	}

	private boolean isCouponTitleUnique(Coupon couponToAdd, Company company) throws CouponSystemException {
		List<Coupon> coupons = company.getCoupons();

		for (Coupon coupon : coupons) {
			if (coupon.getTitle() == couponToAdd.getTitle()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param coupon
	 * @throws CouponSystemException update coupon in database
	 */
	public Coupon updateCoupon(Coupon coupon) throws CouponSystemException {
		if (coupon == null) {
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "coupon is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) {
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "updateCoupon fail");
			}
			Optional<Coupon> optCoupon = couponRepository.findById(coupon.getId());
			if (!optCoupon.isPresent()) {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "coupon is not in database");
			}
			optCoupon.get().setAmount(coupon.getAmount());
			optCoupon.get().setCategory(coupon.getCategory());
			optCoupon.get().setDescription(coupon.getDescription());
			optCoupon.get().setEndDate(coupon.getEndDate());
			optCoupon.get().setImageUrl(coupon.getImageUrl());
			optCoupon.get().setPrice(coupon.getPrice());
			optCoupon.get().setStartDate(coupon.getStartDate());
			optCoupon.get().setTitle(coupon.getTitle());

			return optCoupon.get();
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "updateCoupon fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "updateCoupon fail :(" + e.getMessage(),
					e);
		}
	}

	/**
	 * @param couponId
	 * @throws CouponSystemException delete coupon from database
	 */
	public Coupon deleteCoupon(Long couponId) throws CouponSystemException {
		if (couponId == null) {
			throw new CouponSystemException(HttpStatus.NOT_ACCEPTABLE, "couponId is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {

				Coupon coupon = optCompany.get().removeCoupon(couponId);
				couponRepository.deleteById(couponId);
				System.out.println("success");
				return coupon;
			} else {
				throw new CouponSystemException(HttpStatus.BAD_REQUEST, "deleteCoupon fail: coupon is not in database");
			}
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "deleteCoupon fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, "deleteCoupon fail :(" + e.getMessage(),
					e);
		}
	}

	/**
	 * @return list of all company coupons
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons() throws CouponSystemException {
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) {
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE);
			}
			return optCompany.get().getCoupons();
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE, "getCompanyCoupons fail " + e.getMessage(),
					e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR,
					"getCompanyCoupons fail :(" + e.getMessage(), e);
		}
	}

	/**
	 * @param maxPrice
	 * @return list of company coupons where price less then maxPrice
	 * @throws CouponSystemException
	 */
	public List<Coupon> getCompanyCoupons(double maxPrice) throws CouponSystemException {
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (!optCompany.isPresent()) {
				throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE,
						"companyRepository.findById(id) fail :(");
			}
			List<Coupon> coupons = optCompany.get().getCoupons();
			List<Coupon> couponsToReturn = new ArrayList<Coupon>();
			for (Coupon coupon : coupons) {
				if (coupon.getPrice() <= maxPrice) {
					couponsToReturn.add(coupon);
				}
			}
			return couponsToReturn;
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE,
					"getCompanyCoupons(maxPrice) fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR,
					"getCompanyCoupons(maxPrice) fail :(" + e.getMessage(), e);
		}
	}

	/**
	 * @return the by the id company
	 * @throws CouponSystemException
	 */
	public Company getCompanyDetails() throws CouponSystemException {
		try {
			Optional<Company> optCompany = companyRepository.findById(id);
			if (optCompany.isPresent()) {
				return optCompany.get();
			}
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE,
					"getCompanyDetails(maxPrice) fail: didn't find company in database");
		} catch (CouponSystemException e) {
			throw e;
		} catch (DataAccessException e) {
			throw new CouponSystemException(HttpStatus.SERVICE_UNAVAILABLE,
					"getCompanyDetails(maxPrice) fail " + e.getMessage(), e);
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR,
					"getCompanyDetails(maxPrice) fail :(" + e.getMessage(), e);
		}
	}
}
