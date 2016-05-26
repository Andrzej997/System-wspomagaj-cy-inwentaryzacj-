package pl.polsl.reservations.ejb.dao.impl;

import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservations.ejb.dao.EquipmentDao;
import pl.polsl.reservations.ejb.dao.EquipmentStateDao;
import pl.polsl.reservations.entities.Equipment;
import pl.polsl.reservations.entities.EqupmentState;
import pl.polsl.reservations.interceptors.TransactionalInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class, TransactionalInterceptor.class})
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class EquipmentStateDaoImpl extends AbstractDaoImpl<EqupmentState> implements EquipmentStateDao {

    private static final long serialVersionUID = -2753370797145954647L;

    private EquipmentDao equipmentFacadeRemote;

    public EquipmentStateDaoImpl() throws NamingException {
        super(EqupmentState.class);
    }

    @Override
    public List<Equipment> getEquipmentCollectionById(Number id){
        EqupmentState equipmentType = this.find(id);
        return equipmentType.getEquipmentCollection();
    }

    @Override
    public void remove(EqupmentState entity) {
        getDependencies();

        EqupmentState equpmentState = this.find(entity.getId());
        List<Equipment> equipmentCollection = equpmentState.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment);
        }
        equpmentState.setEquipmentCollection(equipmentCollection);

        super.remove(equpmentState);
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
