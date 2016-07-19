package com.ava.resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**Spring上下文类*/
public class SpringContext implements ApplicationContextAware {

	protected static ApplicationContext context;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
    
    /**
           该方法在bean接口有多个实现的情况下会报错
    @Deprecated
    public static <T> T getBean(Class<T> type){
    	return (T) context.getBean(type);
    }
     */
    
    public static <T> T getBean(String beanName){
    	return (T) context.getBean(beanName);
    }
}
