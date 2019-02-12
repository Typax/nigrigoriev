package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//SOURCE - видна только в Source, после компиляции в байт коде ничего не будет, например @override
// нужна только для разработки кода, если поставить там где не логично, то компалер будет ругаться.

//CLASS - информация в байт код попасть должна, но всё равно через рефлекшен в рантайме считать её не сможите её там не будет
// нужно для трансформаций, ,байт код инструментейшен. ПО УМОЛОЧАНИЮ!!!

//RUNTIME - у большинство аннотаций, которые через рефлекшен можно считать.

//Настройте intellj, чтобы по умолчанию менял дефолт
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
