package com.polmos.webchess.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Helper tool that delivers spring beans to old fashion java classes/services
 * that are not able to autowire dependencies (since they are not beans)
 *
 * @author RobicToNieMaKomu
 */
public class SpringContextProvider implements ApplicationContextAware{

    private final static Logger logger = Logger.getLogger(SpringContextProvider.class);
    public static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        SpringContextProvider.applicationContext = ac;
        logger.debug("Application context loaded into SpringContextProvider");
    }
}
