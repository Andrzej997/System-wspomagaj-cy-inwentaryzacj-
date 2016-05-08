package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;

import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
//@Interceptors({LoggerImpl.class})
@Stateful(mappedName = "EquipmentFacade")
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
