package quoters;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        //достаём class по интерфейсу так как интерфейс не меняяется, а класс имеент название com.sun.$Proxy..
        context.getBean(Quoter.class).sayQuote();
    }
}
/**
 * Spring (Загрузка)
 *
 * 1. BeanDefinitionReader (стрекоза) - прилетает в наш context.xml и считывает bean
 * 2. (стрекоза) кладёт их в BeanDefinition (Map) ключ - id beana,
 *    значение декларация (из какого класса нужно создавать, есть ли init метод и все настройки beana из xml)
 *
 * 3. Есть BeanFactoryPostProcessor он подкручивает BeanDefinition, перед тем как они попадут в BeanFactory и начнутся создаваться бины
 *    Пример - вместо старой реализации класса подставляем ей новую с помощью @DeprecatedClass
 *
 * 4. Как только наши BeanDefinition созданы они идут в BeanFactory
 *    BeanFactory ищет обычные POJO и соединяет их с BeanDefinition
 *    начинает вызывать BeanPostProcessor (postProcessBeforeInitialization, postProcessAfterInitialization) для каждого bean
 *    после отработки postProcessAfterInitialization, ApplicationListener вызывает event refresh
 *    настройеп beana закончина, все аннотации работают
 * 5. После настройки, bean помещается в контейнер (IOC)
 *
 * Информационные факты
 *
 * 1. Все бины которые обозначены синголтонами. Они создаются сразу с context и кладутся в контейнер (один эксземпляр на приложение)
 * 2. Бин ПРОТОТАЙП создаётся, только тогда когда его запрашивают. (Запросили, Spring нашёл, настроил и забыл. Не хранит!)
 * 3. Если прописать destroy для bean он будет работать только для сингалтон если они прописаены.
 */

/**
 * Профилирование @Profiling
 * Нужно чтобы понять сколько отрабатывает каждый метод по времени (ProfilingHandlerBeanPostProcessor)
 *
 * 1. регистрируем наш bean ProfilingController в котором содержится флажок
 * 2. вызывается postProcessBeforeInitialization и смотрим, есть в этом бине @Profiling, то сохраняем в наш кэш (map)
 * 3. вызывается postProcessAfterInitialization, где каждый пришедший bean мы проверяем на наличие в нашем кэш
 * 4. если нашли класс, то мы ловим его и обварачиваем в dinamic proxy и возвращаем как тот же bean (тип), только с новой логикой
 */

/**
 * Трёх фазовый контруктор @PostProxy
 *
 * Последовательноть вызовов
 * 1. Сначало вызовется обычный Java Constructor (1-ый конструктор)
 * 2. вызывается postProcessBeforeInitialization
 * 3. вызывается @PostConstruct (2-рой конструктор) - на этом уровне не работуют аннотации
 * 4. вызывается postProcessAfterInitialization (настройка бинов)
 * 5. когда заканчивается построение ApplicationListener вызывает Event ContextRefreshedEvent
 *    мы его ловим в классе(PostProxyInvokerContextListener)
 * 6. и вызываем метод который обозначен аннотацией @PostProxy (3-тий конструктор)
 *    на этом уровне бин настроен у нас есть транзакция и мы можеем сходить в БД, что бы прогреть кэш
 */



/**
 * ClassPathBeanDefinitionScanner он является (ResourceLoaderAware)
 * создаёт дополнительные BeanDefinitions тех всех аннотаций, которые наследуют аннотацию @component
 */
