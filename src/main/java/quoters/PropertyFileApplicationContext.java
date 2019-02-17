package quoters;

import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class PropertyFileApplicationContext extends GenericApplicationContext {
    protected PropertyFileApplicationContext (String fileName) {
        PropertiesBeanDefinitionReader reader = new PropertiesBeanDefinitionReader(this);//(как стрекоза с xml. Которая залетает и создаёт BeanDefinition)
        int i = reader.loadBeanDefinitions(fileName);// загрузи все BeanDefinition которые ты нашёл, возврашает кол-во найденных
        System.out.println("found "+i+"  beans");
        refresh();
    }

    public static void main(String[] args) {
        PropertyFileApplicationContext context = new PropertyFileApplicationContext("context.properties");
        context.getBean(Quoter.class).sayQuote();
    }
}
