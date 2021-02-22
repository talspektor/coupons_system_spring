package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ClientType;
import app.core.login.LoginManager;
import app.core.services.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController implements ClientController {

	private AdminService service;
	@Autowired
	private LoginManager loginManager;
	
	@Override
	@PostMapping("/login/{email}/{password}")
	public boolean login(@PathVariable String email, @PathVariable String password) {
		System.out.println("AdminController login");
		try {
			service = (AdminService) loginManager.login(email, password, ClientType.ADMINISTRATOR);
			if (service != null) {
				return true;
			}
			throw new CouponSystemException(HttpStatus.BAD_REQUEST, "login fail :(");
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@PostMapping("/add-company")
	public Company addCoumpany(@RequestBody Company company) {
		System.out.println("AdminController addCompany");
		try {
			Company addedCompany = service.addCompany(company);
			return addedCompany;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@PutMapping("/update-company")
	public Company updateCompany(@RequestBody Company company) {
		System.out.println("AdminController updateCompany");
		try {
			Company updatedCompany = service.updateCompany(company);
			return updatedCompany;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/delete-company/{id}")
	public Company deleteCompany(@PathVariable Long id) {
		try {
			Company  deletedCompany = service.deleteCoumpany(id);
			return deletedCompany;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies() {
		System.out.println("AdminController getAllCompanies");
		try {
			List<Company> companies = service.getAllCompanies();
			return companies;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	@GetMapping("/company/{id}")
	public Company getCompany(@PathVariable Long id) {
		System.out.println("AdminController getCompany");
		try {
			Company compnay = service.getCompany(id);
			return compnay;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/company/name/{name}")
	public Company getCompanyByName(@PathVariable String name) throws CouponSystemException {
		System.out.println("AdminController getCompanyByName");
		try {
			Company company = service.getCompanyByName(name);
			return company;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	// ********** Customer Methods ***************** //
	
	@PostMapping("/add-customer")
	public Customer addCustomer(@RequestBody Customer customer) {
		System.out.println("AdminController addCustomer");
		try {
			Customer addedCustomer = service.addCustomer(customer);
			return addedCustomer;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	@PutMapping("/update-customer")
	public Customer updateCustomer(@RequestBody Customer customer) {
		System.out.println("AdminController updateCustomer");
		try {
			Customer updatedCustomer = service.updateCustomer(customer);
			return updatedCustomer;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	@DeleteMapping("/delete-customer/{id}")
	public Customer deleteCustomer(@PathVariable Long id) {
		System.out.println("AdminController deleteCustomer");
		try {
			Customer deletedCustomer = service.deleteCustomer(id);
			return deletedCustomer;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	@GetMapping("/customers")
	public List<Customer>  getAllCustomers() {
		System.out.println("AdminController getAllCustomers");
		try {
			List<Customer> customers = service.getAllCustomer();
			return customers;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		System.out.println("AdminController getCustomer");
		try {
			Customer customer = service.getCustomer(id);
			return customer;
		} catch (CouponSystemException e) {
			throw e;
		} catch (Exception e) {
			throw new CouponSystemException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
