package pl.polsl.reservations.annotations;

import pl.polsl.reservations.privileges.PrivilegeEnum;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 * Annotation that informs about required privilege to execute annotated method
 */
@Inherited
@InterceptorBinding
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface RequiredPrivilege {

    PrivilegeEnum value() default PrivilegeEnum.ADMIN_ACTIONS;
}
