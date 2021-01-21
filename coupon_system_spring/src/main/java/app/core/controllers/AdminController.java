package app.core.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	@PostMapping("/login")
	public boolean login(@RequestBody String email, @RequestBody String password) {
		System.out.println("AdminController login");
		if (email == null || password == null) {
			//TODO: show message to the user
			return false;
		}
		try {
			if (adminService.login(email, password)) {
				//TODO: show message to the user
				return true;
			}
			//TODO: show message to the user
			return false;
		} catch (CouponSystemException e) {
			//TODO: show message to the user
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@PostMapping("/addcompany")
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
	
	@PutMapping("/updatecompany")
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
	
	@DeleteMapping("/deletecompany")
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
	public List<Company> getAllCompanies() {
		System.out.println("AdminController getAllCompanies");
		try {
			return adminService.getAllCompanies();
		} catch (CouponSystemException e) {
			//TODO: show message to the user
			
		}
		return null;
	}
	
	@GetMapping("/company")
	public Company getCompany(@RequestBody Long companyId) {
		System.out.println("AdminController getCompany");
		if (companyId == null) {
			//TODO: show message to the user
			return null;
		}
		try {
			return adminService.getCompany(companyId);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
	
	@GetMapping("/company")
	public Company getCompanyByName(@RequestBody String name) {
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
	
	@PostMapping("/addcustomer")
	public void addCustomer(@RequestBody Customer customer) {
		System.out.println("AdminController addCustomer");
		if (customer == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.addCustomer(customer);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
	}
	
	@PutMapping("updteCustomer")
	public void updateCustomer(Customer customer) {
		System.out.println("AdminController updateCustomer");
		if (customer == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.updateCustomer(customer);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
	}
	
	@DeleteMapping("deletecustomer")
	public void deleteCustomer(Long customerId) {
		System.out.println("AdminController deleteCustomer");
		if (customerId == null) {
			//TODO: show message to the user
			return;
		}
		try {
			adminService.deleteCustomer(customerId);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
	}
	
	@GetMapping("customers")
	public List<Customer> getAllCustomers() {
		System.out.println("AdminController getAllCustomers");
		try {
			return adminService.getAllCustomer();
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
	
	@GetMapping("customer")
	public Customer getCustomer(@RequestBody Long customerId) {
		System.out.println("AdminController getCustomer");
		try {
			return adminService.getCustomer(customerId);
		} catch (CouponSystemException e) {
			//TODO: show message to the user
		}
		return null;
	}
}
