package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseItem<Boolean>> login(@PathVariable String email, @PathVariable String password) {
		System.out.println("AdminController login");
		try {
			service = (AdminService) loginManager.login(email, password, ClientType.ADMINISTRATOR);
			if (service != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseItem<Boolean>(true));
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseItem<Boolean>(false));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Boolean>(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Boolean>(false, e.getMessage()));
		}
	}
	
	@PostMapping("/add-company")
	public void addCoumpany(@RequestBody Company company) throws CouponSystemException {
		System.out.println("AdminController addCompany");
		service.addCompany(company);
	}
	
	@PutMapping("/update-company")
	public void updateCompany(@RequestBody Company company) throws CouponSystemException {
		System.out.println("AdminController updateCompany");
		service.updateCompany(company);
	}
	
	@DeleteMapping("/delete-company/{id}")
	public void deleteCompany(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController deleteCompany");
		service.deleteCoumpany(id);
	}
	
	@GetMapping("/companies")
	public List<Company> getAllCompanies() throws CouponSystemException {
		System.out.println("AdminController getAllCompanies");
		return service.getAllCompanies();
	}
	
	@GetMapping("/company/{id}")
	public Company getCompany(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController getCompany");
		return service.getCompany(id);
	}
	
	@GetMapping("/company/name/{name}")
	public Company getCompanyByName(@PathVariable String name) throws CouponSystemException {
		System.out.println("AdminController getCompanyByName");
		return service.getCompanyByName(name);
	}
	
	// ********** Customer Methods ***************** //
	
	@PostMapping("/add-customer")
	public void addCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController addCustomer");
		service.addCustomer(customer);
	}
	
	@PutMapping("/update-customer")
	public void updateCustomer(@RequestBody Customer customer) throws CouponSystemException {
		System.out.println("AdminController updateCustomer");
		service.updateCustomer(customer);
	}
	
	@DeleteMapping("/delete-customer/{id}")
	public void deleteCustomer(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController deleteCustomer");
		service.deleteCustomer(id);
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() throws CouponSystemException {
		System.out.println("AdminController getAllCustomers");
		return service.getAllCustomer();
	}
	
	@GetMapping("/customer/{id}")
	public Customer getCustomer(@PathVariable Long id) throws CouponSystemException {
		System.out.println("AdminController getCustomer");
		return service.getCustomer(id);
	}
}
