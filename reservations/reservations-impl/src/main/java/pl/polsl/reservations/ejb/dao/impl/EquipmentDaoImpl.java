package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.Query;
import pl.polsl.reservations.ejb.dao.EquipmentDao;
import pl.polsl.reservations.ejb.dao.EquipmentStateDao;
import pl.polsl.reservations.ejb.dao.EquipmentTypeDao;
import pl.polsl.reservations.ejb.dao.RoomDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Equipment data access object implementation
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EquipmentDaoImpl extends AbstractDaoImpl<Equipment> implements EquipmentDao {

    private static final long serialVersionUID = 4691619751998264500L;
    private EquipmentStateDao equipmentStateFacadeRemote;
    private EquipmentTypeDao equipmentTypeFacadeRemote;
    private RoomDao roomFacadeRemote;

    public EquipmentDaoImpl() throws NamingException {
        super(Equipment.class);
    }

    /**
     * Method to get room equipment by room number
     *
     * @param roomNumber int with room number
     * @return list of rooms' equipment
     */
    @Override
    public List<Equipment> getEquipmentByRoomNumber(int roomNumber) {
        Query query = getEntityManager().createNamedQuery("getEquipmentByRoomNumber", Equipment.class);
        query.setParameter("roomNumber", roomNumber);
        return query.getResultList();
    }

    /**
     * Method used to remove entity from database
     *
     * @param entity entity to remove
     */
    @Override
    public void remove(Equipment entity) {
        getDependencies();

        Equipment equipment = this.find(entity.getId());
        /* EqupmentState equipmentState = equipment.getEquipmentState();
        List<Equipment> equipmentCollection = equipmentState.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        equipmentState.setEquipmentCollection(equipmentCollection);
        equipmentStateFacadeRemote.merge(equipmentState);
        equipment.setEquipmentState(null);

        EquipmentType equipmentType = equipment.getEquipmentType();
        equipmentCollection = equipmentType.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        equipmentType.setEquipmentCollection(equipmentCollection);
        equipmentTypeFacadeRemote.merge(equipmentType);
        equipment.setEquipmentType(null);

        Room room = equipment.getRoom();
        equipmentCollection = room.getEquipmentCollection();
        equipmentCollection.remove(equipment);
        room.setEquipmentCollection(equipmentCollection);
        roomFacadeRemote.merge(room);
        equipment.setRoom(null);*/

        super.remove(equipment);
    }

    @Override
    protected void getDependencies() {
        try {
            equipmentStateFacadeRemote = new EquipmentStateDaoImpl();
            roomFacadeRemote = new RoomDaoImpl();
            equipmentTypeFacadeRemote = new EquipmentTypeDaoImpl();
            equipmentStateFacadeRemote.setUserContext(userContext);
            roomFacadeRemote.setUserContext(userContext);
            equipmentTypeFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
