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
import pl.polsl.reservations.entities.EquipmentType;
import pl.polsl.reservations.entities.EqupmentState;
import pl.polsl.reservations.entities.Room;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
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
