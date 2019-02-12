package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

//чтобы следовать конвенции нужно имплементитть BeanPostProcessor
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
    //init метод обозначается @PostConstruct, будет вызываться после метода postProcessBeforeInitialization();
    // зачем нужнв init методы? есть же конструкторы. Но проблема в том, но проблема в том, что конструктор вызывается
    // до настройки бина и мы просто не увидем и будет NPE

    // вызывается до init метода
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                Random random = new Random();
                int i = min + random.nextInt(max - min);
                field.setAccessible(true); // Поля же private и чтобы записать нужно сделать тру
                ReflectionUtils.setField(field, bean, i);
            }
        }
        return bean;
    }
    //вызывается после init метода
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
