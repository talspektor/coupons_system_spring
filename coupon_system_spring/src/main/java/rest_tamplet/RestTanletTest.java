package rest_tamplet;

import app.core.entities.Company;

public class RestTanletTest {

	public static void main(String[] args) {

		try {
			Login login = new Login();
			Admin admin = new Admin(login.adminLogin());
			System.out.println("token: " + admin.getHeaders());
			admin.getCompany(8L);
//			admin.getAllCompanies();
			Company company = admin.getCompany(8L);
			company.setEmail("123");
			admin.updateCompany(company);
//			admin.addCompany(company);
//			admin.deleteCompany(9L);
//			company.setId(8L);
//			company.setEmail("rest_update");
//			admin.updateCompany(company);
//			login.companyLogin("1", "1");			

//			login.customerLogin("www", "www");

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
