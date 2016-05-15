package pl.polsl.reservations.client;

import pl.polsl.reservations.roomManagement.RoomManagementFacadeRemote;
import pl.polsl.reservations.user.UserFacadeRemote;
import pl.polsl.reservations.userManagement.UserManagementFacadeRemote;
import pl.polsl.reservations.schedule.ScheduleFacade;

/**
 *
 * @author matis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lookup l = new Lookup();
        
        //pobranie lookupem dostępnych dla klienta fasad
        //generalnie pobierajcie wedle potrzeb tu macie przykład jak pobrać wszystkie
        UserManagementFacadeRemote userManagementFacadeRemote = 
                (UserManagementFacadeRemote)l.getRemote("UserManagementFacade");
        UserFacadeRemote userFacadeRemote = (UserFacadeRemote) l.getRemote("UserFacade");
        RoomManagementFacadeRemote roomManagementFacadeRemote = 
                (RoomManagementFacadeRemote) l.getRemote("RoomManagementFacade");
        ScheduleFacade scheduleFacadeRemote = (ScheduleFacade) l.getRemote("ScheduleFacade");
        
        //uzycie dowolnej metody zdalnej:
        Long privilige = userFacadeRemote.getUserPrivilege();
        System.out.println("Privilige level: " + privilige);
        
    }
}
