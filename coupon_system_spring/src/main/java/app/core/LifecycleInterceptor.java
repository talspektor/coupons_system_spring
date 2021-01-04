package app.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import app.core.job.DailyJob;

@Component
public class LifecycleInterceptor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof DailyJob) {
			DailyJob dailyJob = (DailyJob)bean;
			Thread thread = new Thread(dailyJob);
			thread.start();
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	
}
