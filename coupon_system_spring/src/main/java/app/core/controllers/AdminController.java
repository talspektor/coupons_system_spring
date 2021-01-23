package app.core.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
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
import app.core.services.AdminService;

@RestController
@RequestMapping("/api")
public class AdminController implements ClientController {
	
	@Autowired
	private AdminService adminService;
	
	// CREATE - add a representation to the server
	@PostMapping("/login/{email}/{password}")
	public boolean login(@PathVariable String email, @PathVariable String password) {
		System.out.println("AdminController login");
		if (email == null || password == null) {
			//TODO: show message to the user
			return false;
		}
		try {
			return adminService.login(email, password);
			//TODO: show message to the user
		} catch (CouponSystemException e) {
			//TODO: show message to the user
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@PostMapping("/add-company")
	public void addCoumpany(@RequestBody Company company) {
		System.out.println("AdminController addCompany");
		if (company == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.addCompany(company);
			//TODO: show message to the user
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		
	}
	
	@PutMapping("/update-company")
	public void updateCompany(@RequestBody Company company) {
		System.out.println("AdminController updateCompany");
		if (company == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.updateCompany(company);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
	}
	
	@DeleteMapping("/delete-company")
	public void deleteCompany(@RequestBody Long companyId) {
		System.out.println("AdminController deleteCompany");
		if (companyId == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.deleteCoumpany(companyId);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies() throws CouponSystemException {
		System.out.println("AdminController getAllCompanies");
		try {
			return adminService.getAllCompanies();
		} catch (CouponSystemException e) {
			throw e;
			
		}
	}
	
	@GetMapping("/company/{id}")
	public Company getCompany(@PathVariable Long id) {
		System.out.println("AdminController getCompany");
		if (id == null) {
			//TODO: show message to the user
			return null;
		}
		try {
			return adminService.getCompany(id);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
	
	@GetMapping("/company/name/{name}")
	public Company getCompanyByName(@PathVariable String name) {
		System.out.println("AdminController getCompanyByName");
		if (name == null) {
			//TODO: show message to the user
			return null;
		}
		try {
			return adminService.getCompanyByName(name);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
	
	// ********** Customer Methods ***************** //
	
	@PostMapping("/add-customer")
	public void addCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController addCustomer");
		try {
			adminService.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw e;
		}
	}
	
	@PutMapping("/updte-customer")
	public void updateCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController updateCustomer");
		try {
			adminService.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw e;
		}
	}
	
	@DeleteMapping("/delete-customer")
	public void deleteCustomer(@RequestBody Long id) throws CouponSystemException {
		System.out.println("AdminController deleteCustomer");
		try {
			adminService.deleteCustomer(id);
		} catch (CouponSystemException e) {
			throw e;
		}
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("AdminController getAllCustomers");
		try {
			return adminService.getAllCustomer();
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
	
	@GetMapping("customer/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		System.out.println("AdminController getCustomer");
		try {
			return adminService.getCustomer(id);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
}
