package org.jboss.jndiTest;

import javax.ejb.Remote;

@Remote
public interface TimerRemote {
	public boolean checkTimerStatus();
    public void startTimer();

}
