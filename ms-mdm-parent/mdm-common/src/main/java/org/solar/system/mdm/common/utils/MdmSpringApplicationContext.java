package org.solar.system.mdm.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MdmSpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		MdmSpringApplicationContext.applicationContext = applicationContext;
	}

	public static synchronized ApplicationContext getApplicationContext() {
		return MdmSpringApplicationContext.applicationContext;
	}

	public static synchronized <T> Object getBean(Class<T> classType) {
		return applicationContext.getBean(classType);
	}

}
