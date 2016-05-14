package pl.polsl.reservationsdatabasebean.logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by matis on 05.05.2016.
 */
public class LoggerImpl {

    private static final Logger log = Logger.getLogger(LoggerImpl.class.getName());

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception
    {
        log.log(Level.INFO, "*** Intercepting call to ReservationsDatabaseBean method: {0}", ctx.getMethod().getName());
        return ctx.proceed();
    }

    @PostConstruct
    public void postConstruct(InvocationContext ctx){
        log.log(Level.INFO, "*** Object: {0} was created", ctx.getTarget().getClass().getName());
    }

    @PreDestroy
    public void preDestroy(InvocationContext ctx){
        log.log(Level.INFO, "*** Object: {0} will be destroyed", ctx.getTarget().getClass().getName());
    }

    @PrePersist
    public void prePersist(Object object){
        log.log(Level.INFO, "*** Entity: {0} will be persisted", object.getClass().getName());
    }

    @PostPersist
    public void postPersist(Object object){
        log.log(Level.INFO, "*** Entity: {0} was persisted", object.getClass().getName());
    }

    @PreRemove
    public void preRemove(Object object){
        log.log(Level.FINEST, "*** Entity: {0} will be removed", object.getClass().getName());
    }


    @PostRemove
    public void postRemove(Object object){
        log.log(Level.FINEST, "*** Entity: {0} was removed", object.getClass().getName());
    }

    @PreUpdate
    public void preUpdate(Object object){
        log.log(Level.INFO, "*** Entity: {0} will be updated", object.getClass().getName());
    }


    @PostUpdate
    public void postUpdate(Object object){
        log.log(Level.INFO, "*** Entity: {0} was updated", object.getClass().getName());
    }


    @PostLoad
    public void postLoad(Object object){
        log.log(Level.INFO, "*** Entity: {0} was loaded", object.getClass().getName());
    }
}
