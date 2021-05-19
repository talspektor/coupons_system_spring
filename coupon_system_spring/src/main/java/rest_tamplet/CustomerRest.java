package rest_tamplet;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;

public class CustomerRest {

	private RestTemplate restTemplate = new RestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	private String baseUrl = "http://localhost:8080/api/";
	
	public CustomerRest(String token) {
		super();
		headers.set("token", token);
	}
	
	
	public HttpHeaders getHeaders() {
		return headers;
	}
	
	public Coupon purchaseCoupon(Long id) {
		System.out.println("purchaseCoupon");
		try {
			String url = baseUrl + "purchase-coupon/" + id;
			HttpEntity<Object> companyHttpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Coupon> response = restTemplate.exchange(url, HttpMethod.PUT, companyHttpEntity, Coupon.class);
			System.out.println("coupon purchase: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public List<Coupon> getCustomerCoupons() {
		System.out.println("getCustomerCoupons");
		try {
			String url = baseUrl + "customer/coupons";
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
	
	public List<Coupon> getAllCoupons() {
		System.out.println("getAllCoupons");
		try {
			String url = baseUrl + "coupons";
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
	
	public List<Coupon>getCouponsByCategory(Category category) {
		System.out.println("getCouponsByCategory");
		try {
			String url = baseUrl + "customer/coupons/category/" + category;
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
	
	public List<Coupon>getCouponsPriceLessThen(double maxPrice) {
		System.out.println("getCouponsPriceLessThen");
		try {
			String url = baseUrl + "customer/coupons/maxPrice/" + maxPrice;
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
	
	public Customer getCustomerDetails() {
		System.out.println("getCustomerDetails");
		try {
			String url = baseUrl + "customer";
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			ResponseEntity<Customer> response = restTemplate.exchange(url,  HttpMethod.GET, httpEntity, Customer.class);
			System.out.println("company: " + response.getBody());
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}
	}
}
