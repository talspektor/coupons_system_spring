package rest_tamplet;


import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.core.entities.Company;
import app.core.entities.Customer;

public class Admin {

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	private String baseUrl = "http://localhost:8080/api/";  
	
	public Admin(String token) {
		super();
		headers.set("token", token);
	}
	
	
	public HttpHeaders getHeaders() {
		return headers;
	}
	
	// Company methods
	public Company addCompany(Company company) {
		System.out.println("addCompany");
		try {
			String url = baseUrl + "add-company";
			HttpEntity<Company> companyHttpEntity = new HttpEntity<Company>(company, headers);
			ResponseEntity<Company> response = restTemplate.postForEntity(url, companyHttpEntity, Company.class);
			System.out.println("copmany added: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company updateCompany(Company company) {
		try {
			String url = baseUrl + "update-company";
			HttpEntity<Company> httpEntity = new HttpEntity<Company>(company, headers);
			ResponseEntity<Company> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Company.class);
			System.out.println("company updated: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company deleteCompany(Long id) {
		try {
			String url = baseUrl + "delete-company/" + id;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Company> response = restTemplate.exchange(url,  HttpMethod.DELETE, httpEntity, Company.class);
			System.out.println("company deleted: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company getCompany(Long id) {
		System.out.println("getCompany");
		try {
			String url = baseUrl + "company/" + id;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Company> response = restTemplate.exchange(url,  HttpMethod.GET, httpEntity, Company.class);
			System.out.println("company: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company getCompanyByName(String name) {
		System.out.println("getCompanyByName");
		try {
			String url = baseUrl + "company/name/" + name;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Company> response = restTemplate.exchange(url,  HttpMethod.GET, httpEntity, Company.class);
			System.out.println("company: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Company> getAllCompanies() {
		System.out.println("getAllCompanies");
		try {
			String url = baseUrl + "companies";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ParameterizedTypeReference<List<Company>> type = new ParameterizedTypeReference<List<Company>>() {
			};
			ResponseEntity<List<Company>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, type);
			System.out.println("companies: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	// ****** Admin customer methods *********
	
	public Customer addCustomer(Customer customer) {
		System.out.println("addCompany");
		try {
			String url = baseUrl + "add-customer";
			HttpEntity<Customer> companyHttpEntity = new HttpEntity<Customer>(customer, headers);
			ResponseEntity<Customer> response = restTemplate.postForEntity(url, companyHttpEntity, Customer.class);
			System.out.println("customer added: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Customer updateCustomer(Customer customer) {
		System.out.println("updateCustomer");
		try {
			String url = baseUrl + "update-customer";
			HttpEntity<Customer> httpEntity = new HttpEntity<Customer>(customer, headers);
			ResponseEntity<Customer> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Customer.class);
			System.out.println("customer updated: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Customer deleteCustomer(Long id) {
		System.out.println("deleteCustomer");
		try {
			String url = baseUrl + "delete-customer/" + id;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Customer> response = restTemplate.exchange(url,  HttpMethod.DELETE, httpEntity, Customer.class);
			System.out.println("company deleted: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Customer getCustoemr(Long id) {
		System.out.println("getCustoemr");
		try {
			String url = baseUrl + "customer/" + id;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Customer> response = restTemplate.exchange(url,  HttpMethod.GET, httpEntity, Customer.class);
			System.out.println("customer: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Customer> getAllCustomers() {
		System.out.println("getAllCustomers");
		try {
			String url = baseUrl + "customers";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ParameterizedTypeReference<List<Customer>> type = new ParameterizedTypeReference<List<Customer>>() {
			};
			ResponseEntity<List<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, type);
			System.out.println("customers: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
}
