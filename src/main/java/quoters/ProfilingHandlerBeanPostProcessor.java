package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    //содержит оригинальное название классов
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingHandlerBeanPostProcessor() throws Exception {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);
        } return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        // Если класс не null, тогда получается на классом стояла аннотация Prifiling и он записался в map в методе postProcessBeforeInitialization()
        // Это означает, что return будем делать не оригинальным объектом, а объектом которым сгенерится с помощью dinamicProxy
        // он создаёт объект из нового класса который сгенерит он же сам на лету.
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), (proxy, method, args) -> {
                // логика которая будет в каждом методе класса который сгенерится на лету, который имплементиркует
                // интерфейсы огиринального класса
                if (controller.isEnabled()) {
                    System.out.println("Профилирую...");
                    long before = System.nanoTime();
                    Object retVal = method.invoke(bean, args);
                    long after = System.nanoTime();
                    System.out.println(after - before); System.out.println("Всё");
                    return retVal;
                } else {
                    return method.invoke(bean, args);
                }
            });
        } return bean;
    }
}
