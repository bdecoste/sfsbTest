package org.jboss.jndiTest;
import javax.ejb.Remote;

@Remote
public interface StatelessBean1Remote {
	
	void call();

}
