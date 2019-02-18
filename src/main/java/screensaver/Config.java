package screensaver;

import org.springframework.context.annotation.*;

import java.awt.*;
import java.util.Random;

@Configuration
//прогоняемся по всему пакету и находим биан сингелтон, для которого не будет применятся новые настройки в том числе и цвет
@ComponentScan(basePackages = "screensaver")
public class Config {
    @Bean
    @Scope("periodical")
    public Color color() {
        Random random = new Random();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    @Bean
    public ColorFrame frame() {
        return new ColorFrame() {
            @Override
            protected Color getColor() {
                return color();
            }
        };
    }

    public static void main(String[] args) throws InterruptedException{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        while (true) {
            context.getBean(ColorFrame.class).showOnRandomPlace();
            Thread.sleep(100);
        }
    }
}

/**
 * Как менять состояния бина (прототайп) если он находится в бине сингел тон?
 * 1. В бине сингел тон заинжектить ApplicationContext и достать наш бин (прототайп)НЕЛЬЗЯ!! Spring внедрять в бизнес код
 * 2. У прототай бина приписать  @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS), но при каждом вызове нашего бина в сингелтоне будет создаваться новый бин
 * 3. Убрать инжект из сингелтона и добавить abstract метод getColor(). В свою очередь в прототайп
 */
