package pl.polsl.reservationsdatabasebeanremote.database.logger;

import pl.polsl.reservationsdatabasebeanremote.database.controllers.AbstractDao;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.logging.*;


/**
 * Created by matis on 05.05.2016.
 * Log zapisuje się z reguły na glassfishu w takiej scieżce
 * \glassfish4\glassfish\domains\domain1\config
 */
@Transactional
@Interceptor
public class LoggerImpl {

    private static final String LOGFILEPATH = "\\log.log";

    private static final Logger log = Logger.getLogger(LoggerImpl.class.getName());

    //static logger configuration
    static {
        try {
            String currentProjetPath = System.getProperty("user.dir") + LOGFILEPATH;
            FileHandler fileHandler = new FileHandler(currentProjetPath);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            log.setLevel(Level.ALL);
            consoleHandler.setLevel(Level.INFO);
            fileHandler.setLevel(Level.FINEST);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            log.addHandler(consoleHandler);
            log.addHandler(fileHandler);
        } catch (SecurityException | IOException ex) {
            log.log(Level.INFO, "Loging to file not posible!!!\n");
            log.log(Level.INFO, ex.getMessage());
        }
    }

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        Object result = null;
        log.log(Level.INFO, "*** Intercepting call to ReservationsDatabaseBean method: {0}\n", ctx.getMethod().getName());
        if (ctx.getMethod().getParameters() != null) {
            Parameter[] parameters = ctx.getMethod().getParameters();
            Arrays.stream(parameters).forEach(parameter ->
                    log.log(Level.FINE, "*** Method parameter: {0} of {1}\n",
                            new String[]{parameter.toString(), parameter.getType().getName()})
            );
        }

        try {
            result = ctx.proceed();
            if (result != null) {
                String resultDataArray[] = {result.toString(), result.getClass().getName()};
                log.log(Level.FINE, "*** Method returned: {0} of {1}\n", resultDataArray);
            }
        } catch (Exception e) {
            log.throwing(ctx.getTarget().getClass().getName(), ctx.getMethod().getName(), e);
            throw e;
        }

        return result;
    }

    @PostConstruct
    public void postConstruct(InvocationContext ctx) {
        log.log(Level.CONFIG, "*** Object: {0} was created\n", ctx.getTarget().getClass().getName());
    }

    @PreDestroy
    public void preDestroy(InvocationContext ctx) {
        if (ctx.getTarget() instanceof AbstractDao) {
            ((AbstractDao) ctx.getTarget()).closeEntityManager();
        }
        log.log(Level.CONFIG, "*** Object: {0} will be destroyed\n", ctx.getTarget().getClass().getName());
    }

    @PrePersist
    public void prePersist(Object object) {
        log.log(Level.CONFIG, "*** Entity: {0} will be persisted\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }

    @PostPersist
    public void postPersist(Object object) {
        log.log(Level.INFO, "*** Entity: {0} was persisted\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }

    @PreRemove
    public void preRemove(Object object) {
        log.log(Level.CONFIG, "*** Entity: {0} will be removed\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }


    @PostRemove
    public void postRemove(Object object) {
        log.log(Level.INFO, "*** Entity: {0} was removed\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }

    @PreUpdate
    public void preUpdate(Object object) {
        log.log(Level.CONFIG, "*** Entity: {0} will be updated\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }


    @PostUpdate
    public void postUpdate(Object object) {
        log.log(Level.CONFIG, "*** Entity: {0} was updated\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }


    @PostLoad
    public void postLoad(Object object) {
        log.log(Level.CONFIG, "*** Entity: {0} was loaded\n", object.getClass().getName());
        log.log(Level.FINE, object.toString());
    }

}
