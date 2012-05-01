package org.jboss.jndiTest;
import javax.ejb.Local;

@Local
public interface StatefulBean1Local {
	
	void setState(String state);
	
	String getState();

}
