package pl.polsl.reservations.annotations;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

/**
 *
 * @author matis
 */
@Inherited
@InterceptorBinding
@Target({TYPE})
@Retention(RUNTIME)
public @interface UserSessionScoped {
    
}
