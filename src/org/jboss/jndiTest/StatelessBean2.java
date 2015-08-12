package org.jboss.jndiTest;

import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.TransactionTimeout;

/**
 * Session Bean implementation class StatelessBean2
 */
@Stateless
@TransactionTimeout(value = 10)
public class StatelessBean2 implements StatelessBean2Remote, StatelessBean2Local {
    /**
     * Default constructor. 
     */
    public StatelessBean2() {
        // TODO Auto-generated constructor stub
    }

}
