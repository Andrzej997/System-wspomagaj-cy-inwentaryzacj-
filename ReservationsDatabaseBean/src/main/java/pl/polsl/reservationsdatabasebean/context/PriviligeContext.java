package pl.polsl.reservationsdatabasebean.context;

import java.util.Objects;
import javax.ejb.Stateful;

/**
 *
 * @author matis
 */
@Stateful
public class PriviligeContext {
    
    private IPrivilige privilige;

    public PriviligeContext() {
    
    }
    
    public void setPriviligeLevel(Integer priviligeLevel){
        for(PriviligesEnum priviligeValue : PriviligesEnum.values()){
            if(Objects.equals(priviligeValue.getValue(), priviligeLevel)){
                this.privilige = priviligeValue.getPrivilige();
            }
        }
    }

    public IPrivilige getPrivilige() {
        return privilige;
    }
    
}
