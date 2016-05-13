package pl.polsl.reservationsdatabasebean.controllers;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import pl.polsl.reservationsdatabasebean.logger.LoggerImpl;
import pl.polsl.reservationsdatabasebeanremote.database.Equipment;
import pl.polsl.reservationsdatabasebeanremote.database.EquipmentType;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentFacadeRemote;
import pl.polsl.reservationsdatabasebeanremote.database.controllers.EquipmentTypeFacadeRemote;

import java.util.List;

/**
 * @author matis
 */
@Interceptors({LoggerImpl.class})
@Stateful
public class EquipmentTypeFacade extends AbstractFacade<EquipmentType> implements EquipmentTypeFacadeRemote {

    private static final long serialVersionUID = -3795169270292695011L;

    private EquipmentFacadeRemote equipmentFacadeRemote;

    public EquipmentTypeFacade() throws NamingException {
        super(EquipmentType.class);
    }

    @Override
    public List<Equipment> getEquipmentCollectionById(Number id){
        EquipmentType equipmentType = this.find(id);
        return equipmentType.getEquipmentCollection();
    }

    @Override
    public void remove(Object id){
        getDepenedencies();

        EquipmentType equipmentType = this.find(id);
        List<Equipment> equipmentCollection = equipmentType.getEquipmentCollection();
        for(Equipment equipment : equipmentCollection){
            equipmentFacadeRemote.remove(equipment.getId());
        }
        equipmentType.setEquipmentCollection(equipmentCollection);

        super.remove(equipmentType.getId());
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
