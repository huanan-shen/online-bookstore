package org.anthony.bs.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {

        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {

        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {

        return applicationContext.getBean(name);
    }

    public static Object getBean(Class<?> requiredType) {

        return applicationContext.getBean(requiredType);
    }

}
