package app.core;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import app.core.exceptions.CouponSystemException;
import app.core.filters.LoginFilter;
import app.core.session.SessionContext;
import app.core.test.Test;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CouponSystemSpringApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CouponSystemSpringApplication.class, args);
		try {
			context.getBean(Test.class).testAll();
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (CouponSystemException e) {
			e.printStackTrace();
		} finally {
//			try {
//				Thread.sleep(7000);
//				context.close();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	@Bean
	public FilterRegistrationBean<LoginFilter> filterRegisrtation(SessionContext sessionContext) {
		FilterRegistrationBean<LoginFilter> filterRegistrationBean = new FilterRegistrationBean<LoginFilter>();
		LoginFilter loginFilter = new LoginFilter(sessionContext);
		filterRegistrationBean.setFilter(loginFilter);
		filterRegistrationBean.addUrlPatterns("/api/*");
		return filterRegistrationBean;
	}
}
