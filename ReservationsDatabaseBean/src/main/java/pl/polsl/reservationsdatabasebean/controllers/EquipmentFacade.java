package pl.polsl.reservationsdatabasebean.controllers;

import java.util.List;
import javax.ejb.Stateful;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    private static final long serialVersionUID = 4691619751998264500L;

    public EquipmentFacade() throws NamingException {
        super(Equipment.class);
    }

    @Override
    public List<Equipment> getEquipmentByRoomNumber(int roomNumber){
        Query query = getEntityManager().createNamedQuery("getEquipmentByRoomNumber", Equipment.class);
        query.setParameter("roomNumber", roomNumber);
        return query.getResultList();
    }
}
