package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CustomerRepository;
import app.core.exceptions.CouponSystemException;

//TODO: make prototype
@Transactional
@Service
@Scope(value = "prototype")
public class AdminService implements ClientService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	public AdminService() {
		super();
	}

	/**
	 * @param email
	 * @param password
	 * @return true if email and password are correct
	 */
	public boolean login(String email, String password) {
		System.out.println("Admin login");
		if (email == "com.admin@admin" && password == "admin") {
			System.out.println("login success :)");
			return true;
		}
		System.out.println("login fail :(");
		return false;
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * add company to database not allow email and password duplication
	 */
	public void addCompany(Company company) throws CouponSystemException {
		System.out.println("Admin addCompany");
		try {
			companyRepository.save(company);
		} catch (Exception e) {
			throw new CouponSystemException("addCompany fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * update company in database (don't change company name) 
	 */
	public void updateCompany(Company company) throws CouponSystemException {
		System.out.println("Admin updateCompany");
		try {
			Optional<Company> companyToUpdate = companyRepository.findById(company.getId());
			if (companyToUpdate.isPresent()) {
				company.setName(companyToUpdate.get().getName());
				companyRepository.save(company);
				System.out.println("updateCompany: " + company);
				return;
			} 
			System.out.println("company: " + company + " is not found is database");
		} catch (Exception e) {
			throw new CouponSystemException("updateCompany fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param companyId
	 * @throws CouponSystemException
	 * delete all company coupon and then delete the company
	 */ 
	public void deleteCoumpany(Long companyId) throws CouponSystemException {
		System.out.println("Admin deleteCompany");
		try {
			companyRepository.deleteById(companyId);
		} catch (Exception e) {
			throw new CouponSystemException("deleteCoumpany fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @return all companies from database
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() throws CouponSystemException {
		System.out.println("Admin getAllCompanies");
		try {
			return (List<Company>) companyRepository.findAll();
		} catch (Exception e) {
			throw new CouponSystemException("getAllCompanies fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param companyId
	 * @return the company with that id
	 * @throws CouponSystemException
	 */
	public Company getCompany(Long companyId) throws CouponSystemException {
		System.out.println("Admin getCompany");
		try {
			Optional<Company> optCompany = companyRepository.findById(companyId);
			if (optCompany.isPresent()) {
				return optCompany.get();
			}
			System.out.println("getCompany fail");
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getCompany fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email is unique
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		System.out.println("Admin addCustomer");
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			throw new CouponSystemException("addCustomer fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * update customer in database 
	 */
	public void updateCustomer(Customer customer) throws CouponSystemException {
		System.out.println("Admin updateCustomer");
		try {
			customerRepository.save(customer);
		} catch (Exception e) {
			throw new CouponSystemException("updateCustomer fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete customer coupon purchases and delete customer from database
	 */
	public void deleteCustomer(Long customerId) throws CouponSystemException {
		System.out.println("Admin deleteCustomer");
		try {
			customerRepository.deleteById(customerId);
		} catch (Exception e) {
			throw new CouponSystemException("deleteCustomer fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @return list of all the customers from database
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		System.out.println("Admin getAllCustomer");
		try {
			return (List<Customer>) customerRepository.findAll();
		} catch (Exception e) {
			throw new CouponSystemException("getAllCustomer fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param customerId
	 * @return the customer with the customer id from database
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(Long customerId) throws CouponSystemException {
		System.out.println("Admin getCustomer");
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customerId);
			if(optCustomer.isPresent()) {
				return optCustomer.get();
			}
			return null;
		} catch (Exception e) {
			throw new CouponSystemException("getCustomer fail " + e.getMessage(), e);
		}
	}
}
