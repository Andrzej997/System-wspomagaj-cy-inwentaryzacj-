package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EqupmentState;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentStateFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentStateFacade extends AbstractFacade<EqupmentState> implements EquipmentStateFacadeRemote {

    private static final long serialVersionUID = -2753370797145954647L;

    private EquipmentFacadeRemote equipmentFacadeRemote;

    public EquipmentStateFacade() throws NamingException {
        super(EqupmentState.class);
    }

    @Override
    public List<Equipment> getEquipmentCollectionById(Number id){
        EqupmentState equipmentType = this.find(id);
        return equipmentType.getEquipmentCollection();
    }

    @Override
    public void remove(Object id){
        getDepenedencies();

        EqupmentState equpmentState = this.find(id);
        List<Equipment> equipmentCollection = equpmentState.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment.getId());
        }
        equpmentState.setEquipmentCollection(equipmentCollection);

        super.remove(equpmentState.getStateId());
    }

    protected void getDepenedencies(){
        try {
            equipmentFacadeRemote = new EquipmentFacade();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        Integer priviligeLevel = this.getPriviligeContext().getPriviligeLevel();
        equipmentFacadeRemote.setPriviligeLevel(priviligeLevel);
    }
}
