package com.company.project.authz.domain.shared;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;


@Service
public class BeanProvider implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    public BeanProvider() {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
            BeanProvider.applicationContext = context;
    }

//    public static void initialize(ApplicationContext applicationContext) {
//        BeanProvider.applicationContext = applicationContext;
//    }

    /**
     * Get Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanId);
    }

}