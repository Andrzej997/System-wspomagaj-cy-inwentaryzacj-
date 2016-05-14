package pl.polsl.reservationsdatabasebeanremote.database.logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.*;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * Created by matis on 05.05.2016.
 * Log zapisuje się z reguły na glassfishu w takiej scieżce
 * \glassfish4\glassfish\domains\domain1\config
 */
public class LoggerImpl {

    private static final String LOGFILEPATH = "\\log.log";

    private static final Logger log = Logger.getLogger(LoggerImpl.class.getName());
    private static final Logger fileLogger = Logger.getLogger(LoggerImpl.class.getName());

    private static FileHandler fh = logToFile();

    private static FileHandler logToFile() {
        try {
            String currentProjetPath = System.getProperty("user.dir") + LOGFILEPATH;
            FileHandler fileHandler = new FileHandler(currentProjetPath);
            log.setLevel(Level.INFO);
            fileLogger.addHandler(fileHandler);
            fileLogger.setLevel(Level.FINER);
            fileLogger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            return fileHandler;
        } catch (SecurityException | IOException ex) {
            log.log(Level.INFO, "Loging to file not posible!!!");
            log.log(Level.INFO, ex.getMessage());
        }
        return null;
    }

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception
    {
        log.log(Level.INFO, "*** Intercepting call to ReservationsDatabaseBean method: {0}", ctx.getMethod().getName());
        fileLogger.log(Level.INFO, "*** Intercepting call to ReservationsDatabaseBean method: {0}", ctx.getMethod().getName());
        if (ctx.getMethod().getParameters() != null) {
            fileLogger.log(Level.FINE, "*** Method parameters: {0}", ctx.getMethod().getParameters().toString());
        }
        return ctx.proceed();
    }

    @PostConstruct
    public void postConstruct(InvocationContext ctx){
        fileLogger.log(Level.CONFIG, "*** Object: {0} was created", ctx.getTarget().getClass().getName());
    }

    @PreDestroy
    public void preDestroy(InvocationContext ctx){
        fileLogger.log(Level.CONFIG, "*** Object: {0} will be destroyed", ctx.getTarget().getClass().getName());
    }

    @PrePersist
    public void prePersist(Object object){
        fileLogger.log(Level.CONFIG, "*** Entity: {0} will be persisted", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }

    @PostPersist
    public void postPersist(Object object){
        log.log(Level.INFO, "*** Entity: {0} was persisted", object.getClass().getName());
        fileLogger.log(Level.INFO, "*** Entity: {0} was persisted", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }

    @PreRemove
    public void preRemove(Object object){
        fileLogger.log(Level.CONFIG, "*** Entity: {0} will be removed", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }


    @PostRemove
    public void postRemove(Object object){
        log.log(Level.INFO, "*** Entity: {0} was removed", object.getClass().getName());
        fileLogger.log(Level.INFO, "*** Entity: {0} was removed", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }

    @PreUpdate
    public void preUpdate(Object object){
        fileLogger.log(Level.CONFIG, "*** Entity: {0} will be updated", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }


    @PostUpdate
    public void postUpdate(Object object){
        fileLogger.log(Level.CONFIG, "*** Entity: {0} was updated", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }


    @PostLoad
    public void postLoad(Object object){
        fileLogger.log(Level.CONFIG, "*** Entity: {0} was loaded", object.getClass().getName());
        fileLogger.log(Level.FINE, object.toString());
    }
}
