package org.jboss.sfsbTest;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

@MessageDriven(activationConfig=
{
		@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Queue"),
		@ActivationConfigProperty(propertyName="destination",propertyValue="queue/test")
})
public class TestMDB implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println("Received JMS Message " + message);

	}

}
