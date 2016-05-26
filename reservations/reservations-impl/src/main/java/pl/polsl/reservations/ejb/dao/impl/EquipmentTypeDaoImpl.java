package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservations.ejb.dao.EquipmentDao;
import pl.polsl.reservations.ejb.dao.EquipmentTypeDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EquipmentType;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EquipmentTypeDaoImpl extends AbstractDaoImpl<EquipmentType> implements EquipmentTypeDao {

    private static final long serialVersionUID = -3795169270292695011L;

    private EquipmentDao equipmentFacadeRemote;

    public EquipmentTypeDaoImpl() throws NamingException {
        super(EquipmentType.class);
    }

    @Override
    public List<Equipment> getEquipmentCollectionById(Number id){
        EquipmentType equipmentType = this.find(id);
        return equipmentType.getEquipmentCollection();
    }

    @Override
    public void remove(EquipmentType entity) {
        getDependencies();

        EquipmentType equipmentType = this.find(entity.getId());
        List<Equipment> equipmentCollection = equipmentType.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment);
        }
        equipmentType.setEquipmentCollection(equipmentCollection);

        super.remove(equipmentType);
    }

    @Override
    protected void getDependencies() {
        try {
            equipmentFacadeRemote = new EquipmentDaoImpl();
            equipmentFacadeRemote.setUserContext(userContext);
        } catch (NamingException e) {
        }
    }
}
