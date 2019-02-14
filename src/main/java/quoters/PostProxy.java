package quoters;
// Все методы которые аннотированы @PostProxy запускаются сами в тот момент когда всё уже настроено
// и все проксисгенерировались. Помогает нам в этом Listener

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PostProxy {
}
