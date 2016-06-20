package pl.polsl.reservations.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import pl.polsl.reservations.ejb.local.AbstractDao;

/**
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * DAO methods interceptor to add them transactional scope
 */
@Transactional
@Interceptor
public class TransactionalInterceptor {

    /**
     * Invokes method in transaction
     * @param ctx InvocationContext object
     * @return method result
     * @throws Exception when something goes wrong
     */
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

    /**
     * Method to get user transaction from abstractDao
     * @param ctx InvocationContext object
     * @return current UserTransaction object
     */
    public UserTransaction getUserTransaction(InvocationContext ctx) {
        if (ctx.getTarget() instanceof AbstractDao) {
            return ((AbstractDao) ctx.getTarget()).getUserTransaction();
        } else {
            return null;
        }
    }
}
