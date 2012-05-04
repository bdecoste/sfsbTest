package org.jboss.sfsbTest;

import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.jndiTest.EntityTester;
import org.jboss.jndiTest.StatefulBean1;
import org.jboss.jndiTest.StatefulBean1Local;
import org.jboss.jndiTest.StatefulBean1Remote;
import org.jboss.jndiTest.TestEntity;

/**
 * Servlet implementation class SfsbServlet
 */
@WebServlet("/SfsbServlet")
public class SfsbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String STATE = "STATE";
	private static final String SFSB = "SFSB";
	private static final String MODIFIED = "MODIFIED";

    /**
     * Default constructor. 
     */
    public SfsbServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		callSfsb(session);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		callSfsb(session);
	}
	
	protected void callSfsb(HttpSession session) throws ServletException {
		try {
	    	
			String remotings = System.getenv("OPENSHIFT_JBOSS_CLUSTER_REMOTING");
			
			StringTokenizer tokenizer = new StringTokenizer(remotings, ",");
			
			while (tokenizer.hasMoreTokens()){
				String remoting = tokenizer.nextToken();
				runTest(session, remoting);
			}
		} catch (Exception e){
    		e.printStackTrace();
    		throw new ServletException(e);
    	}
	}
	
	protected void runTest(HttpSession session, String remoting) throws Exception {
    	Properties props = new Properties();
      
    	Properties jndiProps = new Properties();
    	jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    	jndiProps.put(InitialContext.PROVIDER_URL, "remote://" + remoting);
    	InitialContext jndiContext = new InitialContext(jndiProps);
    	System.out.println("!!! jndi props " + jndiContext.getEnvironment());
    	
    	String state = (String)session.getAttribute(STATE);
    	System.out.println("HTTPSession state " + state);
    	if (state == null){
	    	session.setAttribute(STATE, MODIFIED);
    	}
    	
    	StatefulBean1Remote sfsb = (StatefulBean1Remote)session.getAttribute(SFSB);
    	System.out.println("HTTPSession sfsb " + sfsb);
    	
    	if (sfsb == null) {
    		String jndiBinding = "java:global/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Remote";
    		sfsb = (StatefulBean1Remote) jndiContext.lookup(jndiBinding);
    		session.setAttribute(SFSB, sfsb);    		
    	}
    	
    	String jndiBinding = "java:global/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Local";
    	StatefulBean1Local local = (StatefulBean1Local) jndiContext.lookup(jndiBinding);
	
    	System.out.println("State1 " + sfsb.getState());
    	sfsb.setState("MODIFIED");
    	System.out.println("State2 " + sfsb.getState());
    	
    	//jndiBinding = "java:global/sfsbTest/EntityTesterBean!org.jboss.jndiTest.EntityTester";
    	//EntityTester tester = (EntityTester)jndiContext.lookup(jndiBinding);
    	//tester.createEntity(1);
    	//tester.findEntity(1);
    	
    	JmsClient sm = new JmsClient(remoting);
        String msg = "Testing123";

        sm.sendMessageOverJMS(msg);
        
        System.out.println("JMS message sent");
	}
	
	public static void main(String args[]) throws Throwable
    {        	    	
		Properties props = new Properties();
	      
    	Properties jndiProps = new Properties();
    	jndiProps.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
    	jndiProps.put(InitialContext.PROVIDER_URL, "remote://127.0.250.1:4447");
    	InitialContext jndiContext = new InitialContext(jndiProps);
    	
    	String jndiBinding = "java:global/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Remote";
    	StatefulBean1Remote sfsb = (StatefulBean1Remote) jndiContext.lookup(jndiBinding);
    }

}
