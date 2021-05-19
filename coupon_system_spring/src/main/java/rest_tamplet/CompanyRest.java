package rest_tamplet;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.core.entities.Company;
import app.core.entities.Coupon;

public class CompanyRest {

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	private String baseUrl = "http://localhost:8080/api/";
	
	public CompanyRest(String token) {
		super();
		headers.set("token", token);
	}
	
	public HttpHeaders getHeaders() {
		return headers;
	}
	
	public Coupon addCoupon(Coupon coupon) {
		System.out.println("addCoupon");
		try {
			String url = baseUrl + "add-coupon";
			HttpEntity<Coupon> companyHttpEntity = new HttpEntity<Coupon>(coupon, headers);
			ResponseEntity<Coupon> response = restTemplate.postForEntity(url, companyHttpEntity, Coupon.class);
			System.out.println("coupon added: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Coupon updateCoupon(Coupon coupon) {
		System.out.println("updateCoupon");
		try {
			String url = baseUrl + "update-coupon";
			HttpEntity<Coupon> httpEntity = new HttpEntity<Coupon>(coupon, headers);
			ResponseEntity<Coupon> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Coupon.class);
			System.out.println("coupon updated: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Coupon deleteCoupon(Long id) {
		System.out.println("deleteCoupon");
		try {
			String url = baseUrl + "delete-coupon/" + id;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Coupon> response = restTemplate.exchange(url,  HttpMethod.DELETE, httpEntity, Coupon.class);
			System.out.println("coupon deleted: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Coupon> getCouponsPriceLessThen(double maxPrice) {
		System.out.println("getCouponsPriceLessThen");
		try {
			String url = baseUrl + "company/coupons/" + maxPrice;
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ParameterizedTypeReference<List<Coupon>> type = new ParameterizedTypeReference<List<Coupon>>() {
			};
			ResponseEntity<List<Coupon>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, type);
			System.out.println("companies: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Coupon> getAllCoupons() {
		System.out.println("getAllCoupons");
		try {
			String url = baseUrl + "company/coupons";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ParameterizedTypeReference<List<Coupon>> type = new ParameterizedTypeReference<List<Coupon>>() {
			};
			ResponseEntity<List<Coupon>> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, type);
			System.out.println("coupons: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Company getCompanyDetails() {
		System.out.println("getCompanyDetails");
		try {
			String url = baseUrl + "company";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Company> response = restTemplate.exchange(url,  HttpMethod.GET, httpEntity, Company.class);
			System.out.println("company: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
}
