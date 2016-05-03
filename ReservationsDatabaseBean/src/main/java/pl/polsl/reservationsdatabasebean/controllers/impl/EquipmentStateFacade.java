package pl.polsl.reservationsdatabasebean.controllers.impl;

import pl.polsl.reservationsdatabasebean.entities.EqupmentState;
import pl.polsl.reservationsdatabasebean.controllers.EquipmentStateFacadeRemote;

import javax.ejb.Stateful;
import javax.naming.NamingException;

/**
 * @author matis
 */
@Stateful(mappedName = "EquipmentStateFacade")
public class EquipmentStateFacade extends AbstractFacade<EqupmentState> implements EquipmentStateFacadeRemote {

    private static final long serialVersionUID = -2753370797145954647L;

    public EquipmentStateFacade() throws NamingException {
        super(EqupmentState.class);
    }

}
