package util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletContextExample implements ServletContextListener{
	ServletContext context;
		
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
            com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();

        } catch (InterruptedException e) {
        	e.printStackTrace();
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();

            if (driver.getClass().getClassLoader() == cl) {

                try {
                    DriverManager.deregisterDriver(driver);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            } else {
               System.out.println("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader");
            }
        }
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}