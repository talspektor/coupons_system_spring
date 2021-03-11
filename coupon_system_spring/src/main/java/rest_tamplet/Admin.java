package rest_tamplet;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.core.entities.Company;

public class Admin {

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	
	public Admin(String token) {
		super();
		headers.set("token", token);
	}
	
	
	public HttpHeaders getHeaders() {
		return headers;
	}


	public Company addCompany(Company company) throws Exception {
		System.out.println("addCompany");
		try {
			String url = "http://localhost:8080/api/add-company";
			HttpEntity<Company> companyHttpEntity = new HttpEntity<Company>(company, headers);
			ResponseEntity<Company> response = restTemplate.postForEntity(url, companyHttpEntity, Company.class);
			System.out.println("addCompany: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company updateCompany(Company company) throws Exception {
		try {
			String url = "http://localhost:8080/api/update-company";
			HttpEntity<Company> httpEntity = new HttpEntity<Company>(company, headers);
			ResponseEntity<Company> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Company.class);
			System.out.println("company updated: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company deleteCompany(Long id) throws Exception {
		try {
			String url = "http://localhost:8080/api/delete-company/" + id;
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
			String url = "http://localhost:8080/api/company/" + id;
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
			String url = "http://localhost:8080/api/companies";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Company[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Company[].class);
			List<Company> companies = Arrays.asList(response.getBody());
			System.out.println("getAllCompanies: " + companies);
			return companies;
		} catch (Exception e) {
			throw e;
		}
	}
}
