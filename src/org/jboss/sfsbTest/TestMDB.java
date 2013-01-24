package org.jboss.sfsbTest;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;


@MessageDriven(activationConfig=
{
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination",propertyValue="queue/test")
})
//@org.jboss.ejb3.annotation.Pool(value="mdb-pool")
public class TestMDB implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("Received JMS Message " + ((TextMessage)message).getText());
			Thread.sleep(5000);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
