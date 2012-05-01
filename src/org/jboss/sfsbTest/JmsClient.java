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
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.sasl.JBossSaslProvider;

public class JmsClient {
	String remoting;
	
	static {
	   Security.addProvider(new JBossSaslProvider());
	}
	
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
            ObjectMessage tm = null;

            producer = session.createProducer(queue);
            tm = session.createObjectMessage(message);
            tm.setStringProperty(PROPERTY, "Testing123");
            producer.send(tm);
        } finally {
            if(producer != null) {
            	producer.close();
            }
            cleanupJMSConnection();
        }
    }

    public void setupJMSConnection() throws JMSException, NamingException
    {
    	Properties jndiProps = new Properties();
    	jndiProps.put(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
    	//jndiProps.put(InitialContext.PROVIDER_URL, "remote://3f8f5f57ac-bdecoste92.dev.rhcloud.com:35545");
    	jndiProps.put(InitialContext.PROVIDER_URL, "remote://" + remoting);
    	InitialContext context = new InitialContext(jndiProps);
    	
    	System.out.println("!!!!!!!! props " + jndiProps);
    	
    	ConnectionFactory cf = (ConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
    	queue = (Queue) context.lookup("jms/queue/test");
    	conn = cf.createConnection("guest", "guest");
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
        String msg = "Testing123";

        sm.sendMessageOverJMS(msg);
    }
}
