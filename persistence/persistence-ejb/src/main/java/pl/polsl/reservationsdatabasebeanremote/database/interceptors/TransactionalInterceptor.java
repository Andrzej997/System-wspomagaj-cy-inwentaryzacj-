package pl.polsl.reservationsdatabasebeanremote.database.interceptors;

import pl.polsl.reservationsdatabasebeanremote.database.controllers.AbstractDao;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;

/**
 * Created by matis on 15.05.2016.
 */
@Transactional
@Interceptor
public class TransactionalInterceptor {

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        Object result = null;
        try {
            if (getUserTransaction(ctx) != null) {
                getUserTransaction(ctx).begin();
            }
            result = ctx.proceed();
            if (getUserTransaction(ctx) != null) {
                getUserTransaction(ctx).commit();
            }
        } catch (Exception e) {
            if (getUserTransaction(ctx) != null) {
                getUserTransaction(ctx).rollback();
            }
            throw e;
        }
        return result;
    }

    public UserTransaction getUserTransaction(InvocationContext ctx) {
        if (ctx.getTarget() instanceof AbstractDao) {
            return ((AbstractDao) ctx.getTarget()).getUserTransaction();
        } else {
            return null;
        }
    }
}
