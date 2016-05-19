package pl.polsl.reservationsdatabasebean.controllers;

import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.PrivilegeLevelEnum;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentDao;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentStateDao;
import pl.polsl.reservationsdatabasebeanremote.database.interceptors.TransactionalInterceptor;
import pl.polsl.reservationsdatabasebeanremote.database.logger.LoggerImpl;

import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import java.util.List;

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

        EqupmentState equpmentState = this.find(entity.getStateId());
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
        } catch (NamingException e) {
            e.printStackTrace();
        }
        PrivilegeLevelEnum privilegeLevel = this.getPriviligeLevel();
        equipmentFacadeRemote.setPriviligeLevel(privilegeLevel);
    }
}
