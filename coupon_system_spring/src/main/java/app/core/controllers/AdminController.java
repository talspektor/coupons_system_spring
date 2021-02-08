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
					.body(new ResponseItem<Boolean>(false, "Login fail :("));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Boolean>(false, e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Boolean>(false, e.getMessage()));
		}
	}
	
	@PostMapping("/add-company")
	public ResponseEntity<ResponseItem<Company>> addCoumpany(@RequestBody Company company) {
		System.out.println("AdminController addCompany");
		try {
			Company addedCompany = service.addCompany(company);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(addedCompany));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
	}
	
	@PutMapping("/update-company")
	public ResponseEntity<ResponseItem<Company>> updateCompany(@RequestBody Company company) {
		System.out.println("AdminController updateCompany");
		try {
			Company updatedCompany = service.updateCompany(company);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(updatedCompany));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
		
	}
	
	@DeleteMapping("/delete-company/{id}")
	public ResponseEntity<ResponseItem<Company>> deleteCompany(@PathVariable Long id) {
		try {
			Company  deletedCompany = service.deleteCoumpany(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(deletedCompany));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
	}
	
	@GetMapping("/companies")
	public ResponseEntity<ResponseItem<List<Company>>> getAllCompanies() {
		System.out.println("AdminController getAllCompanies");
		try {
			List<Company> companies = service.getAllCompanies();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Company>>(companies));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Company>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Company>>(e.getMessage()));
		}
		
	}
	
	@GetMapping("/company/{id}")
	public ResponseEntity<ResponseItem<Company>> getCompany(@PathVariable Long id) {
		System.out.println("AdminController getCompany");
		try {
			Company compnay = service.getCompany(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(compnay));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
	}
	
	@GetMapping("/company/name/{name}")
	public ResponseEntity<ResponseItem<Company>> getCompanyByName(@PathVariable String name) throws CouponSystemException {
		System.out.println("AdminController getCompanyByName");
		try {
			Company company = service.getCompanyByName(name);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Company>(company));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Company>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Company>(e.getMessage()));
		}
	}
	
	// ********** Customer Methods ***************** //
	
	@PostMapping("/add-customer")
	public ResponseEntity<ResponseItem<Customer>> addCustomer(@RequestBody Customer customer) {
		System.out.println("AdminController addCustomer");
		try {
			Customer addedCustomer = service.addCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Customer>(addedCustomer)); 
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Customer>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Customer>(e.getMessage()));
		}
		
	}
	
	@PutMapping("/update-customer")
	public ResponseEntity<ResponseItem<Customer>> updateCustomer(@RequestBody Customer customer) {
		System.out.println("AdminController updateCustomer");
		try {
			Customer updatedCustomer = service.updateCustomer(customer);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Customer>(updatedCustomer));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Customer>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Customer>(e.getMessage()));
		}
		
	}
	
	@DeleteMapping("/delete-customer/{id}")
	public ResponseEntity<ResponseItem<Customer>> deleteCustomer(@PathVariable Long id) {
		System.out.println("AdminController deleteCustomer");
		try {
			Customer deletedCustomer = service.deleteCustomer(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Customer>(deletedCustomer));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Customer>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Customer>(e.getMessage()));
		}
		
	}
	
	@GetMapping("/customers")
	public ResponseEntity<ResponseItem<List<Customer>>>  getAllCustomers() {
		System.out.println("AdminController getAllCustomers");
		try {
			List<Customer> customers = service.getAllCustomer();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<List<Customer>>(customers));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<List<Customer>>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<List<Customer>>(e.getMessage()));
		}
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<ResponseItem<Customer>> getCustomer(@PathVariable Long id) {
		System.out.println("AdminController getCustomer");
		try {
			Customer customer = service.getCustomer(id);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseItem<Customer>(customer));
		} catch (CouponSystemException e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseItem<Customer>(e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseItem<Customer>(e.getMessage()));
		}
	}
}
