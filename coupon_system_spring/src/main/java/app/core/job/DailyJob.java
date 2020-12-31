package app.core.job;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.core.repositories.CouponRepository;

@Component
public class DailyJob implements Runnable {
	
	@Autowired
	private CouponRepository couponRepository;
	private boolean quit;
	private final int daysInMilliseconds = 1000*60*60*24;
	private Thread jobThread;

	public DailyJob() {
		super();
	}
	
	@Override
	public void run() {
		jobThread = Thread.currentThread();
		try {
			System.out.println("CouponExpirationDailyJob run");
			while (!quit) {
				//TODO: delete expired coupons
				Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
//				couponRepository.removeByEndDateLessThen(sqlDate);
				System.out.println("CouponExpirationDailyJob deleteExpierdCoupons");
				Thread.sleep(daysInMilliseconds);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getCause());
		} catch (InterruptedException e) {
			System.out.println("Thread was interupted and will stoped");
		}
		System.out.println("job thread finished");
	}
	public void stop() {
		this.quit = true;
		jobThread.interrupt();
	}
}
