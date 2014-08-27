package org.jboss.sfsbTest;

import java.io.IOException;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.mail.Address;
//import javax.mail.Message;
import javax.mail.Provider;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
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
import org.jboss.jndiTest.StatelessBean1Remote;
import org.jboss.jndiTest.TestEntity;

//import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.*;

/**
 * Servlet implementation class SfsbServlet
 */
@WebServlet("/SfsbServlet")
public class SfsbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String STATE = "STATE";
	private static final String SFSB = "SFSB";
	private static final String MODIFIED = "MODIFIED";
	
	Logger LOG = Logger.getLogger(SfsbServlet.class); 

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

		response.getWriter().write("JEE Test Servlet");
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
			
			LOG.info("########################### callSfsb ########################");
			
			//MysqlTest.initConnection();
			
			//testActiveMQ();
			
	/*	    for (int i = 0 ; i < 10 ; ++i) {
				StringTokenizer tokenizer = new StringTokenizer(remotings, ",");
				
				while (tokenizer.hasMoreTokens()){
					String remoting = tokenizer.nextToken();
					runTest(session, remoting);
				}
			} */
			
			for (int i = 0 ; i < 1 ; ++i)
				runTest(session, "localhost:5445");
			
//			runTest(session, "be673bcb5b-bdecoste26e.dev.rhcloud.com[35545]");
//			runTest(session, "scaledas7-bdecoste26e.dev.rhcloud.com[35540]");
			
			//testMail();
		} catch (Exception e){
    		e.printStackTrace();
    		throw new ServletException(e);
    	}
	}
/*	
	protected void testActiveMQ() throws Exception {
		
		String ip = System.getenv("OPENSHIFT_ACTIVEMQ_IP");
		String port = System.getenv("OPENSHIFT_ACTIVEMQ_OPENWIRE_PORT");
		String host = System.getenv("OPENSHIFT_ACTIVEMQ_OPENWIRE_HOST");
		if (host != null && host.trim().length() > 0) {
			ip = host;
			port = System.getenv("OPENSHIFT_ACTIVEMQ_OPENWIRE_PORT");
		}
		consumeAmqMessage(ip, port);
		sendAmqMessage(ip, port);
	}
		
	protected void consumeAmqMessage(String ip, String port) throws Exception {
        Thread consumerThread = new Thread(new AmqConsumer(ip, port));
        consumerThread.setDaemon(false);
        consumerThread.start();
	}
	
	public class AmqConsumer implements Runnable, ExceptionListener {
		
		String ip;
		String port;
		
		public AmqConsumer(String ip, String port) {
			this.ip = ip;
			this.port = port;
		} 

        public void run() {
            try {
            	
            	System.out.println("!!!!! consuming " + ip + " " + port);
            	

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + ip + ":" + port);

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                connection.setExceptionListener(this);

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue("TEST.FOO");

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message
                Message message = consumer.receive(1000);

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                } else {
                    System.out.println("Received: " + message);
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }

	protected void sendAmqMessage(String ip, String port) throws Exception {
				
		System.out.println("!!!!!! sending ip " + ip + " " + port);
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://" + ip + ":" + port);

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("TEST.FOO");

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        // Create a messages
        String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
	}
*/	
	protected void viewJndi(Context ctx, String path) throws Exception {
		System.out.println("pair '" + path + "'");
		NamingEnumeration<NameClassPair> enumeration = ctx.list(path);
		while (enumeration.hasMoreElements()){
			NameClassPair pair = enumeration.next();
			System.out.println("  " + pair.getName() + " " + pair.getClassName());
		}
	}
	
	protected void runTest(HttpSession session, String remoting) throws Exception {
		System.out.println("-------------------------------");
    	Properties props = new Properties();
      
    	Properties jndiProps = new Properties();
    	jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    	jndiProps.put(InitialContext.PROVIDER_URL, "remote://" + remoting);
    	InitialContext jndiContext = new InitialContext(jndiProps);
    	System.out.println("!!! jndi props " + jndiContext.getEnvironment());
    	//viewJndi(jndiContext, "");
    	//viewJndi(jndiContext, "queue");
    	//viewJndi(jndiContext, "ejb");
    	
    	String state = (String)session.getAttribute(STATE);
    	System.out.println("HTTPSession state " + state);
    	if (state == null){
	    	session.setAttribute(STATE, MODIFIED);
    	}
    	
    	StatefulBean1Remote sfsb = (StatefulBean1Remote)session.getAttribute(SFSB);
    	System.out.println("HTTPSession sfsb " + sfsb);
    	
    	if (sfsb == null) {
    		String jndiBinding = "ejb:/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Remote?stateful";
    		sfsb = (StatefulBean1Remote) jndiContext.lookup(jndiBinding);
    		session.setAttribute(SFSB, sfsb);    		
    	} else {
    		String jndiBinding = "ejb:/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Remote?stateful";
    		StatefulBean1Remote remote = (StatefulBean1Remote) jndiContext.lookup(jndiBinding);
    		System.out.println("Calling remote setState");
    		remote.setState("REMOTE");
    	}
    	
    	String jndiBinding = "java:global/sfsbTest-1.0/StatefulBean1!org.jboss.jndiTest.StatefulBean1Local";
    	StatefulBean1Local local = (StatefulBean1Local) jndiContext.lookup(jndiBinding);
    	System.out.println("local bean " + local);
    	
    	jndiBinding = "ejb:/sfsbTest-1.0/StatelessBean1!org.jboss.jndiTest.StatelessBean1Remote";
    	StatelessBean1Remote stateless = (StatelessBean1Remote) jndiContext.lookup(jndiBinding);
    	System.out.println("stateless bean " + stateless);
    	for (int i = 0 ; i < 5 ; ++i){
    		stateless.call();
    	}
	
    	System.out.println("State1 " + sfsb.getState());
    	sfsb.setState("MODIFIED");
    	System.out.println("State2 " + sfsb.getState());
    	
    	long value = System.currentTimeMillis();
    	long id = 1104;
    	jndiBinding = "java:global/sfsbTest-1.0/EntityTesterBean!org.jboss.jndiTest.EntityTester";
    	EntityTester tester = (EntityTester)jndiContext.lookup(jndiBinding);
    	TestEntity entity = tester.findEntity(id);
    	if (entity == null) {
    		System.out.println("Creating entity " + value);
    		entity = tester.createEntity(id, value);
    	} else {
    		System.out.println("found entity " + entity.getValue());
    	}
    	
 //   	JmsClient sm = new JmsClient(remoting);
 //       String msg = "Testing123";

 //       sm.sendMessageOverJMS(msg);
	}
	
/*	protected void testMail() throws Exception {
		InitialContext ctx = new InitialContext();
		javax.mail.Session session = (javax.mail.Session) ctx.lookup("java:jboss/mail/Default");
		MimeMessage msg = new MimeMessage(session);
		msg.setSubject("TB12");
		msg.setContent("Go Pats!", "text/plain");
		Address to = new InternetAddress("bdecoste@gmail.com");
		msg.addRecipient(Message.RecipientType.TO, to);
		Transport trans = session.getTransport("smtps");
		trans.connect("smtp.gmail.com", "bdecoste", "TomBr@dy12");
		trans.sendMessage(msg, msg.getAllRecipients());
		trans.close();
	}*/
	
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
