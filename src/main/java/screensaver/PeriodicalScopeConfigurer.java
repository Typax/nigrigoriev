package screensaver;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * Добавлен новый Scope для того, чтобы беан изменялся не каждый раз когда запрашивается (цвет)
 * а через 3 секунды
 */
public class PeriodicalScopeConfigurer implements Scope {
    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        return null;
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
