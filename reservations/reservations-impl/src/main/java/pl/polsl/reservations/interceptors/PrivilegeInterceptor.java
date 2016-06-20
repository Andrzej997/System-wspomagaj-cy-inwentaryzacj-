package pl.polsl.reservations.interceptors;

import java.lang.reflect.Method;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import pl.polsl.reservations.annotations.RequiredPrivilege;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.ejb.remote.AbstractBusinessFacadeImpl;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * Method privilege level interceptor to check method execution privilege by method annotation
 */
@RequiredPrivilege
@Interceptor
public class PrivilegeInterceptor {

    /**
     * Executes method only when user has privilege to execution
     * @param ctx InvocationContext object
     * @return execution result
     * @throws Exception when user has no access to execute method
     */
    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        Object result = null;
        Method calledMethod = ctx.getMethod();
        Object target = ctx.getTarget();
        if (target instanceof AbstractBusinessFacadeImpl) {
            UserContext userContext = ((AbstractBusinessFacadeImpl) target).getCurrentUserContext();
            System.out.println("User certificate: " + ((AbstractBusinessFacadeImpl) target).getUserCertificate());
            if (checkMethodPrivileges(calledMethod, userContext)) {
                result = ctx.proceed();
            } else {
                throw new UnauthorizedAccessException("You didn't have access to such functionality");
            }
        } else {
            ctx.proceed();
        }
        return result;
    }

    /**
     * Check if Method is annotated with RequiredPrivilege annotation
     * @param calledMethod Method object
     * @param userContext UserContext object, to get current user data
     * @return true if user has privilege to execute method
     * @throws Exception if he has not;
     */
    private boolean checkMethodPrivileges(Method calledMethod, UserContext userContext) throws Exception {
        RequiredPrivilege privilegeAnnotation = calledMethod.getAnnotation(RequiredPrivilege.class);
        if (privilegeAnnotation != null) {
            PrivilegeEnum requiredPrivilege = privilegeAnnotation.value();
            return userContext.checkPrivilege(requiredPrivilege);
        } else {
            return true;
        }
    }
}
