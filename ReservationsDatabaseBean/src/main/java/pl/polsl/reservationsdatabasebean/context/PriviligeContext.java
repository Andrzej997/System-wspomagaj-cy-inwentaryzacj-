package pl.polsl.reservationsdatabasebean.context;

import java.util.Objects;
import javax.persistence.EntityManager;
import pl.polsl.reservationsdatabasebeanremote.database.context.IPrivilige;

/**
 * @author matis
 */
public class PriviligeContext {

    private IPrivilige privilige;

    public PriviligeContext() {
        privilige = null;
    }

    public void setPriviligeLevel(Integer priviligeLevel) {
        for (PriviligesEnum priviligeValue : PriviligesEnum.values()) {
            if (Objects.equals(priviligeValue.getValue(), priviligeLevel)) {
                this.privilige = priviligeValue.getPrivilige();
            }
        }
    }

    public EntityManager getEntityManager() {
        return privilige.getEntityManager();
    }

}
