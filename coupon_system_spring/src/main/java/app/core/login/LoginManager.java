package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class LoginManager {
	@Autowired
	private ApplicationContext context;
	
	/**
	 * @param email
	 * @param password
	 * @param clientType
	 * @return the service
	 * @throws CouponSystemException
	 */
	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {
		switch (clientType) {
		case ADMINISTRATOR:
			AdminService adminService = context.getBean(AdminService.class);
			return adminService.login(email, password) ? adminService : null;
		case COMPNY:
			CompanyService companyService = context.getBean(CompanyService.class);
			return companyService.login(email, password) ? companyService : null;
		case CUSTOMER:
			CustomerService customerService = context.getBean(CustomerService.class);
			return customerService.login(email, password) ? customerService : null;
		}
		return null;
	}
}
