package app.core.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "company")
public class CompanyEntitiy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	
	private List<CouponEntity> coupons;
	public CompanyEntitiy() {
		super();
	}
	public CompanyEntitiy(String name, String email, String password) {
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
	public List<CouponEntity> getCoupons() {
		return coupons;
	}
	public void setCoupons(List<CouponEntity> coupons) {
		this.coupons = coupons;
	}
	@Override
	public String toString() {
		return "CompanyEntitiy [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	
	
}
