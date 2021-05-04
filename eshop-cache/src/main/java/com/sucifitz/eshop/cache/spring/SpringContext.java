package com.sucifitz.eshop.cache.spring;

import lombok.Data;
import org.springframework.context.ApplicationContext;

/**
 * spring 上下文
 *
 * @author szh
 * @date 2021/5/4
 */
public class SpringContext {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContext.applicationContext = applicationContext;
    }
}
