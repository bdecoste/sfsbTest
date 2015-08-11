package org.jboss.jndiTest;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class TimerService {
  
    @Schedule(second="*/1", minute="*",hour="*", persistent=false)
    public void doWork(){
        System.out.println("Timer!!!!!!!!!!!!!!!!!");
    }
}
