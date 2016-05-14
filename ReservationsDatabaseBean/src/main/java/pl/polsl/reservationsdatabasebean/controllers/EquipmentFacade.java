package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.Room;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentStateFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.RoomFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EquipmentFacade extends AbstractFacade<Equipment> implements EquipmentFacadeRemote {

    private static final long serialVersionUID = 4691619751998264500L;
    private EquipmentStateFacadeRemote equipmentStateFacadeRemote;
    private EquipmentTypeFacadeRemote equipmentTypeFacadeRemote;
    private RoomFacadeRemote roomFacadeRemote;

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
    public void remove(Equipment entity) {
        getDependencies();

        Equipment equipment = this.find(entity.getId());
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

        super.remove(equipment);
    }

    @Override
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
