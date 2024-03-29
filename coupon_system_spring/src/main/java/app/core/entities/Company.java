package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@OneToMany(mappedBy = "company", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Coupon> coupons;
	
	public Company() {
		super();
	}
	public Company(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Coupon> getCoupons() {
		return coupons;
	}
	
	public Coupon getCoupon(String name) {
		for (Coupon coupon : coupons) {
			if (coupon.getTitle().equals(name)) {
				return coupon;
			}
		}
		return null;
	}
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public void addCoupon(Coupon coupon) {
		if(coupons == null) {
			coupons = new ArrayList<Coupon>();
		}
		coupon.setCompany(this);
		coupons.add(coupon);
	}
	
	public Coupon removeCoupon(Long couponId) {
		if(couponId == null) { return null; }
		for (Coupon coupon : coupons) {
			if (couponId == coupon.getId()) {
				coupons.remove(coupon);
				return coupon;
			}
		}
		return null;
	}
	
	public void deleteCoupon(Coupon coupon) {
		if(coupons != null && coupons.contains(coupon)) {
			coupons.remove(coupon);
		} else {
			System.out.println("deleteCoupon: coupon - " + coupon.getId() + "not found");
		}
	}
	
	@Override
	public String toString() {
		return "CompanyEntitiy [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
