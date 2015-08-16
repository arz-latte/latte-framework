package at.arz.latte.framework.services.schedule;

import javax.ejb.Stateless;

@Stateless
public class HelloService {
    public String sayHello(){
        return "Hello from control: " + System.currentTimeMillis();
    }
}