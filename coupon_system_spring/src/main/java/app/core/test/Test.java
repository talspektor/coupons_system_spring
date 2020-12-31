package app.core.test;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.job.DailyJob;
import app.core.login.LoginManager;



@Component
public class Test {

	@Autowired
	private LoginManager loginManager;
	@Autowired
	private ApplicationContext context;

	public Test() {
		super();
	}
	@Transactional
	public void testAll() throws CouponSystemException {
		// Start the daily job
		DailyJob job = context.getBean(DailyJob.class);
		Thread thread = new Thread(job);
		thread.start();
		//**********************
		// Clients:
		// Administrator
		context.getBean(TestAdmin.class).test();
		// Company
		context.getBean(TestCompany.class).test();
		// Customer
		context.getBean(TestCustomer.class).test();
		
		cleenClose(job);	
	}
	
	private void cleenClose(DailyJob job) throws CouponSystemException {
		// Stop the daily job
		job.stop();
		// Close all connections
		System.out.println("closeAllConnections");
		//TODO: close all
	}
}
