package br.ufba.dcc.mestrado.computacao.web.listener;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author leandro.ferreira
 * 
 * @see http://plumbr.eu/permgen
 *
 */
public class JdbcDriverLeakPreventerListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		Enumeration<Driver> drivers = DriverManager.getDrivers(); 
		while (drivers.hasMoreElements()) { 
			Driver driver = drivers.nextElement(); 
			if (driver.getClass().getClassLoader() == this.getClass().getClassLoader()) { 
				try { 
					DriverManager.deregisterDriver(driver); 
				} catch (SQLException e) { 
					e.printStackTrace(); 
				} 
			}
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}

}
