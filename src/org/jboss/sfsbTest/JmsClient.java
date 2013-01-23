/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.jboss.sfsbTest;

import java.security.Security;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.sasl.JBossSaslProvider;

public class JmsClient {
	protected String remoting;
	protected static int toggle = 0;
	//static {
	//   Security.addProvider(new JBossSaslProvider());
	//}
	
	public JmsClient(String remoting){
		this.remoting = remoting;
	}
	
	private static final String PROPERTY = "SFSBTEST";
	Connection conn;
    Session session;
    Queue queue;

    public void sendMessageOverJMS(String message) throws JMSException, NamingException {
        MessageProducer producer = null;

    	setupJMSConnection();
        try {
            TextMessage tm = null;

            producer = session.createProducer(queue);
            tm = session.createTextMessage();
            tm.setText(Long.toString(System.currentTimeMillis()));
            producer.send(tm);
            System.out.println("JMS message sent " + tm.getText());
        } finally {
            if(producer != null) {
            	producer.close();
            }
            cleanupJMSConnection();
        }
    }

    public void setupJMSConnection() throws JMSException, NamingException
    {
  /*  	String gearName = System.getenv("OPENSHIFT_GEAR_UUID");
    	String targetGearName = null;
    	if (toggle == 0) {
    		toggle = 1;
    		targetGearName="be673bcb5bcb4b4fbaa3d0c4a4c0bcdd";
    	} else {
    		toggle = 0;
    		targetGearName="d16ac89498474f4a831a8e4637825912";
    	}
    	System.out.println("!!!!! gearName " + gearName + " " + targetGearName);*/
    	
    	Context context = null;
    	ConnectionFactory cf = null;
  
    	Properties jndiProps = new Properties();
    	jndiProps.put(InitialContext.URL_PKG_PREFIXES, "org.jboss.as.naming.interfaces:org.jboss.ejb.client.naming");
    	jndiProps.put(InitialContext.PROVIDER_URL, "remote://" + remoting);
    	System.out.println("!!!!!!!! remoting " + remoting);
    	context = new InitialContext(jndiProps);
    	
    	System.out.println("!!!!!!!! props " + context.getEnvironment());
    
 //   	if (gearName.equals(targetGearName)) {
    		System.out.println("!!! RemoteConnectionFactory");
    		cf = (ConnectionFactory) context.lookup("RemoteConnectionFactory");
 //   	} else {
 //   		System.out.println("!!! RemoteGearConnectionFactory");
 //   		cf = (ConnectionFactory) context.lookup("java:jboss/exported/jms/RemoteGearConnectionFactory");
 //   	}
   // 	queue = (Queue) context.lookup("java:jboss/exported/jms/queue/test"); // + targetGearName);
    	queue = (Queue) context.lookup("queue/test");
    	conn = cf.createConnection("admin", "admin");
    	session = conn.createSession(false, QueueSession.AUTO_ACKNOWLEDGE);
    	conn.start();

    }

    public void cleanupJMSConnection() throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String args[]) throws Throwable
    {        	    	
    	JmsClient sm = new JmsClient("");
        String msg = Long.toString(System.currentTimeMillis());

        sm.sendMessageOverJMS(msg);
    }
}
