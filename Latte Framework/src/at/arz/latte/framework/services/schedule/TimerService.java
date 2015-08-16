package at.arz.latte.framework.services.schedule;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class TimerService {
    @EJB
    HelloService helloService;
  
    @Schedule(second="*/10", minute="*",hour="*", persistent=false)
    public void doWork(){
        System.out.println("timer: " + helloService.sayHello());
    }
}
