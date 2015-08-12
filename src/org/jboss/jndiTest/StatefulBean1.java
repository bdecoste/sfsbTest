package org.jboss.jndiTest;

import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.Clustered;
import org.jboss.ejb3.annotation.TransactionTimeout;

@Stateful
//@Clustered
@TransactionTimeout(value = 10)
public class StatefulBean1 implements StatefulBean1Local, StatefulBean1Remote {
	
	private String state = "INITIAL";

    /**
     * Default constructor. 
     */
    public StatefulBean1() {
        // TODO Auto-generated constructor stub
    }
    
    public void setState(String state) {
    	System.out.println("StatefulBean new state " + state);
    	this.state = state;
    }
	
	public String getState() {
		return state;
	}

}
