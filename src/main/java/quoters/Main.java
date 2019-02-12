package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        while (true) {
            Thread.sleep(100);
            //достаём class по интерфейсу так как интерфейс не меняяется, а класс имеент название com.sun.$Proxy..
            context.getBean(Quoter.class).sayQuote();
        }
    }
}
