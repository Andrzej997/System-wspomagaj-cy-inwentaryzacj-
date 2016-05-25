package pl.polsl.reservations.interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import pl.polsl.reservations.annotations.PrivilegeLevel;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 *
 * @author matis
 */
@PrivilegeLevel
@Interceptor
public class PrivilegeInterceptor {

    @EJB
    private UserContext userContext;

    @AroundInvoke
    public Object methodInterceptor(InvocationContext ctx) throws Exception {
        Object result = null;
        Method calledMethod = ctx.getMethod();
        if (checkMethodPrivileges(calledMethod)) {
            result = ctx.proceed();
        } else {
            throw new UnauthorizedAccessException("You didn't have access to such functionality");
        }
        return result;
    }

    private boolean checkMethodPrivileges(Method calledMethod) throws Exception {
        Annotation[] declaredAnnotations = calledMethod.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotations) {
            if (annotation instanceof PrivilegeLevel) {
                String privilegeLevel = ((PrivilegeLevel) annotation).privilegeLevel();
                if(privilegeLevel == null || privilegeLevel.equals("") 
                        || privilegeLevel.equalsIgnoreCase("NONE") || privilegeLevel.equalsIgnoreCase("NULL")){
                    return true;
                }
                PrivilegeEnum privilegeEnum = PrivilegeEnum.getPrivilege(privilegeLevel);
                return userContext.checkPrivilege(privilegeEnum);
            }
        }
        return true;
    }

}
