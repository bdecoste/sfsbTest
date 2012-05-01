package org.jboss.jndiTest;
import javax.ejb.Remote;

@Remote
public interface StatefulBean1Remote {
	
	void setState(String state);
	
	String getState();

}
