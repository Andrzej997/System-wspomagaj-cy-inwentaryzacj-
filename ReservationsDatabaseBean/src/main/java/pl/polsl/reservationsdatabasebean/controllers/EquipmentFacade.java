package pl.polsl.reservationsdatabasebean.controllers;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentStateFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    private EquipmentStateFacadeRemote equipmentStateFacadeRemote;

    private EquipmentTypeFacadeRemote equipmentTypeFacadeRemote;

    private RoomFacadeRemote roomFacadeRemote;

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

    @Override
    public void remove(Object id){
        getDependencies();

        Equipment equipment = this.find(id);
        EqupmentState equipmentState = equipment.getEquipmentState();
        List<Equipment> equipmentCollection = equipmentState.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        equipmentState.setEquipmentCollection(equipmentCollection);
        equipmentStateFacadeRemote.merge(equipmentState);

        EquipmentType equipmentType = equipment.getEquipmentType();
        equipmentCollection = equipmentType.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        equipmentType.setEquipmentCollection(equipmentCollection);
        equipmentTypeFacadeRemote.merge(equipmentType);

        Room room = equipment.getRoomId();
        equipmentCollection = room.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        room.setEquipmentCollection(equipmentCollection);
        roomFacadeRemote.merge(room);

        super.remove(equipment.getId());
    }

    protected void getDependencies() {
        try {
            equipmentStateFacadeRemote = new EquipmentStateFacade();
            roomFacadeRemote = new RoomFacade();
            equipmentTypeFacadeRemote = new EquipmentTypeFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        equipmentStateFacadeRemote.setPriviligeLevel(priviligeLevel);
        equipmentTypeFacadeRemote.setPriviligeLevel(priviligeLevel);
        roomFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
