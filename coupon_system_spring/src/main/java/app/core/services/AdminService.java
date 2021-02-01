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
	public boolean login(String email, String password) throws CouponSystemException {
		System.out.println("Admin login");
		if (email == null || password == null) {
			throw new CouponSystemException("login fail :( email or password are null");
		}
		if (email.equals("com.admin@admin") && password.equals("admin")) {
			System.out.println("login success :)");
			return true;
		}
		System.out.println("login fail :(");
		return false;
	}
	
	// *********** Company Methods ************* //
	
	/**
	 * @param company
	 * @throws CouponSystemException
	 * add company to database email and name must by unique
	 */
	public void addCompany(Company company) throws CouponSystemException {
		System.out.println("Admin addCompany");
		if(company == null) {
			throw new CouponSystemException("company is null...");
		}
		try {
			if(isCompanyNameExists(company.getName()) || isCompanyEmailExists(company.getEmail())) {
				throw new CouponSystemException("cant add company name and email must by unique");
			}
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
		if (company == null) {
			throw new CouponSystemException();
		}
		try {
			Optional<Company> companyToUpdate = companyRepository.findById(company.getId());
			if (companyToUpdate.isPresent()) {
				company.setName(companyToUpdate.get().getName());
				companyRepository.save(company);
				System.out.println("updateCompany: " + company);
				return;
			}
			throw new CouponSystemException("company: " + company + " is not found is database");
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
		if (companyId == null) {
			throw new CouponSystemException("companyId is null");
		}
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
			throw new CouponSystemException("getAllCompanies fail :(" + e.getMessage(), e);
		}
	}
	
	/**
	 * @param companyId
	 * @return the company with that id
	 * @throws CouponSystemException
	 */
	public Company getCompany(Long companyId) throws CouponSystemException {
		System.out.println("Admin getCompany");
		if (companyId == null) {
			throw new CouponSystemException("companyId is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findById(companyId);
			if (optCompany.isPresent()) {
				return optCompany.get();
			}
			throw new CouponSystemException("getCompany fail: company not found in database");
		} catch (Exception e) {
			throw new CouponSystemException("getCompany fail :(" + e.getMessage(), e);
		}
	}
	
	public Company getCompanyByName(String name) throws CouponSystemException {
		System.out.println("Admin getCompanyByName");
		if (name == null) {
			throw new CouponSystemException("name is null");
		}
		try {
			Optional<Company> optCompany = companyRepository.findByName(name);
			if (optCompany.isPresent()) {
				return optCompany.get();
			}
			throw new CouponSystemException("getCompanyByName fail: company not found in database");
		} catch (Exception e) {
			throw new CouponSystemException("getCompanyByName fail " + e.getMessage(), e);
		}
	}
	
	// ********** Customer Methods ***************** //
	
	/**
	 * @param customer
	 * @throws CouponSystemException
	 * add customer to database if email and password are unique
	 */
	public void addCustomer(Customer customer) throws CouponSystemException {
		System.out.println("Admin addCustomer");
		if (customer == null) {
			throw new CouponSystemException("customer is null");
		}
		try {
			if(isCustomerEmailExists(customer.getEmail())) {
				System.out.println("You can't add customer email and password most be unique");
				return;
			}
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
		if (customer == null) {
			throw new CouponSystemException("customer is null");
		}
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customer.getId());
			if (!optCustomer.isPresent()) {
				throw new CouponSystemException("Costomer not found in database");
			}
			if (customer.getEmail() != optCustomer.get().getEmail()) {
				if(isCustomerEmailExists(customer.getEmail())) {
					throw new CouponSystemException("You can't update customer email most be unique");
				}
			}
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
		if (customerId == null) {
			throw new CouponSystemException("customerId is null");
		}
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
		if (customerId == null) {
			throw new CouponSystemException("customerId is null");
		}
		try {
			Optional<Customer> optCustomer = customerRepository.findById(customerId);
			if(optCustomer.isPresent()) {
				return optCustomer.get();
			}
			throw new CouponSystemException("Costomer with id=" + customerId + " not found in database");
		} catch (Exception e) {
			throw new CouponSystemException("getCustomer fail " + e.getMessage(), e);
		}
	}
	
	//************** Company Unique check  Methods ********************* //
	
	/**
	 * @param name
	 * @return true if company with given name is in database
	 * @throws CouponSystemException
	 */
	private boolean isCompanyNameExists(String name) throws CouponSystemException {
		try {
			return companyRepository.existsByName(name);
		} catch (Exception e) {
			throw new CouponSystemException("isCompanyNameUnique fail " + e.getMessage(), e);
		}
	}
	
	/**
	 * @param email
	 * @return true if company with given email is in database
	 * @throws CouponSystemException
	 */
	private boolean isCompanyEmailExists(String email) throws CouponSystemException {
		try {
			return companyRepository.existsByEmail(email);
		} catch (Exception e) {
			throw new CouponSystemException("isCompanyEmailUnique fail " + e.getMessage(), e);
		}
	}
	
	//************** Customer Unique check  Methods ********************* //
	
	/**
	 * @param email
	 * @return true if customer with email is exist is database
	 * @throws CouponSystemException
	 */
	private boolean isCustomerEmailExists(String email) throws CouponSystemException {
		try {
			return customerRepository.existsByEmail(email);
		} catch (Exception e) {
			throw new CouponSystemException("isCustomerEmailUniqiue fail " + e.getMessage(), e);
		}
	}
}
