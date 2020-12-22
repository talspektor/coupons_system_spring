package app.core.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "compsny_id")
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
	
	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	
	public void addCoupon(Coupon coupon) {
		if(coupons == null) {
			coupons = new ArrayList<Coupon>();
		}
		coupons.add(coupon);
		System.out.println("addCoupon: coupon - " + coupon.getId() + " was added");
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