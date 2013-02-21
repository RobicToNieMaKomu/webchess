package com.polmos.webchess.service;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service
public class ContextCleaner implements ServletContextListener {

    private static Logger logger = Logger.getLogger(ContextCleaner.class);
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            ClassLoader driverclassLoader = driver.getClass().getClassLoader();
            ClassLoader thisClassLoader = this.getClass().getClassLoader();
            if (driverclassLoader != null && thisClassLoader != null && driverclassLoader.equals(thisClassLoader)) {
                try {
                    logger.warn("Deregistering: " + driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException x) {
            logger.fatal(x.toString(), x);
        }
        Connection con = null;
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/web_chess";
            String user = "postgres";
            String pass = "fancypassword";
            con = DriverManager.getConnection(url, user, pass);
            logger.warn("connection established: " + con.toString());
        } catch (SQLException x) {
            logger.fatal(x.toString(), x);
        }
    }
}
