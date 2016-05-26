package pl.polsl.reservations.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import pl.polsl.reservations.annotations.PrivilegeLevel;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.ejb.remote.AbstractBusinessFacadeImpl;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 *
 * @author matis
 */
@PrivilegeLevel
@Interceptor
public class PrivilegeInterceptor {

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

    private boolean checkMethodPrivileges(Method calledMethod, UserContext userContext) throws Exception {
        Annotation[] declaredAnnotations = calledMethod.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if (annotation instanceof PrivilegeLevel) {
                String privilegeLevel = ((PrivilegeLevel) annotation).privilegeLevel();
                if (privilegeLevel == null || privilegeLevel.equals("")
                        || privilegeLevel.equalsIgnoreCase("NONE") || privilegeLevel.equalsIgnoreCase("NULL")) {
                    return true;
                }
                PrivilegeEnum privilegeEnum = PrivilegeEnum.getPrivilege(privilegeLevel);
                return userContext.checkPrivilege(privilegeEnum);
            }
        }
        return true;
    }

}
