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
public class AdminService {
	
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
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
	public void addCompany(Company company) {
		System.out.println("Admin addCompany");
		companyRepository.save(company);
	}
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * update company in database (don't change company name) 
	 */
	public void updateCompany(Company company) {
		System.out.println("Admin updateCompany");
		Optional<Company> companyToUpdate = companyRepository.findById(company.getId());
		if (companyToUpdate.isPresent()) {
			company.setName(companyToUpdate.get().getName());
			companyRepository.save(company);
			System.out.println("updateCompany: " + company);
		} else {
			System.out.println("company: " + company + " is not found is database");
		}
	}
	
	/**
	 * @param companyId
	 * @throws CouponSystemException
	 * delete all company coupon and then delete the company
	 */ 
	public void deleteCoumpany(Long companyId) {
		System.out.println("Admin deleteCompany");
		companyRepository.deleteById(companyId);
	}
	
	/**
	 * @return all companies from database
	 * @throws CouponSystemException
	 */
	public List<Company> getAllCompanies() {
		System.out.println("Admin getAllCompanies");
		Iterable<Company> companies = companyRepository.findAll();
		return (List<Company>) companies;
	}
	
	/**
	 * @param companyId
	 * @return the company with that id
	 * @throws CouponSystemException
	 */
	public Company getCompany(Long companyId) {
		System.out.println("Admin getCompany");
		Optional<Company> optCompany = companyRepository.findById(companyId);
		if (optCompany.isPresent()) {
			System.out.println("getCompany success");
			return optCompany.get();
		}
		System.out.println("getCompany fail");
		return null;
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email is unique
	 */
	public void addCustomer(Customer customer) {
		System.out.println("Admin addCustomer");
		customerRepository.save(customer);
	}
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * update customer in database 
	 */
	public void updateCustomer(Customer customer) {
		System.out.println("Admin updateCustomer");
		customerRepository.save(customer);
	}
	
	/**
	 * @param customerId
	 * @throws CouponSystemException
	 * delete customer coupon purchases and delete customer from database
	 */
	public void deleteCustomer(Long customerId) throws CouponSystemException {
		System.out.println("Admin deleteCustomer");
		customerRepository.deleteById(customerId);
	}
	
	/**
	 * @return list of all the customers from database
	 * @throws CouponSystemException
	 */
	public List<Customer> getAllCustomer() throws CouponSystemException {
		System.out.println("Admin getAllCustomer");
		Iterable<Customer> customers = customerRepository.findAll();
		return (List<Customer>) customers;
	}
	
	/**
	 * @param customerId
	 * @return the customer with the customer id from database
	 * @throws CouponSystemException
	 */
	public Customer getCustomer(Long customerId) throws CouponSystemException {
		System.out.println("Admin getCustomer");
		Optional<Customer> optCustomer = customerRepository.findById(customerId);
		if(optCustomer.isPresent()) {
			return optCustomer.get();
		}
		return null;
	}
}
