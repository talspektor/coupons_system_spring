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
	public boolean login(@PathVariable String email, @PathVariable String password) throws CouponSystemException {
		System.out.println("AdminController login");
		return adminService.login(email, password);
	}
	
	@PostMapping("/add-company")
	public void addCoumpany(@RequestBody Company company) throws CouponSystemException {
		System.out.println("AdminController addCompany");
		adminService.addCompany(company);
	}
	
	@PutMapping("/update-company")
	public void updateCompany(@RequestBody Company company) throws CouponSystemException {
		System.out.println("AdminController updateCompany");
		adminService.updateCompany(company);
	}
	
	@DeleteMapping("/delete-company")
	public void deleteCompany(@RequestBody Long companyId) throws CouponSystemException {
		System.out.println("AdminController deleteCompany");
		adminService.deleteCoumpany(companyId);
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies() throws CouponSystemException {
		System.out.println("AdminController getAllCompanies");
		return adminService.getAllCompanies();
	}
	
	@GetMapping("/company/{id}")
	public Company getCompany(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController getCompany");
		return adminService.getCompany(id);
	}
	
	@GetMapping("/company/name/{name}")
	public Company getCompanyByName(@PathVariable String name) throws CouponSystemException {
		System.out.println("AdminController getCompanyByName");
		return adminService.getCompanyByName(name);
	}
	
	// ********** Customer Methods ***************** //
	
	@PostMapping("/add-customer")
	public void addCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController addCustomer");
		adminService.addCustomer(customer);
	}
	
	@PutMapping("/updte-customer")
	public void updateCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController updateCustomer");
		adminService.updateCustomer(customer);
	}
	
	@DeleteMapping("/delete-customer")
	public void deleteCustomer(@RequestBody Long id) throws CouponSystemException {
		System.out.println("AdminController deleteCustomer");
		adminService.deleteCustomer(id);
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() throws CouponSystemException {
		System.out.println("AdminController getAllCustomers");
		return adminService.getAllCustomer();
	}
	
	@GetMapping("customer/{id}")
	public Customer getCustomer(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController getCustomer");
		return adminService.getCustomer(id);
	}
}
