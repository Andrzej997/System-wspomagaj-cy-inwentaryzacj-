package pl.polsl.reservationsdatabasebean.logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.*;
import java.util.logging.Logger;


/**
 * Created by matis on 05.05.2016.
 */
public class LoggerImpl {

    private static final Logger log = Logger.getLogger(LoggerImpl.class.getName());

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception
    {
        log.info("*** Intercepting call to ReservationsDatabaseBean method: "
                + ctx.getMethod().getName());
        return ctx.proceed();
    }

    @PostConstruct
    public void postConstruct(InvocationContext ctx){
        log.info("*** Object: "
                + ctx.getConstructor().getName() + "was created");
    }

    @PreDestroy
    public void preDestroy(InvocationContext ctx){
        log.info("*** Object: "
                + ctx.getConstructor().getName() + "will be destroyed");
    }

    @PrePersist
    public void prePersist(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "will be persisted");
    }

    @PostPersist
    public void postPersist(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "was persisted");
    }

    @PreRemove
    public void preRemove(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "will be removed");
    }


    @PostRemove
    public void postRemove(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "was removed");
    }

    @PreUpdate
    public void preUpdate(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "will be updated");
    }


    @PostUpdate
    public void postUpdate(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "was updated");
    }


    @PostLoad
    public void postLoad(Object object){
        log.info("*** Entity:" + object.getClass().getName() + "was loaded");
    }
}
