package org.jboss.jndiTest;

import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.TransactionTimeout;

/**
 * Session Bean implementation class StatelessBean1
 */
@Stateless
@TransactionTimeout(value = 10)
public class StatelessBean1 implements StatelessBean1Remote, StatelessBean1Local {

    /**
     * Default constructor. 
     */
    public StatelessBean1() {
        // TODO Auto-generated constructor stub
    }
    
    public void call(){
    	System.out.println("Stateless called");
    }

}
