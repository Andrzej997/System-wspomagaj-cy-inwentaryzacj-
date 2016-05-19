package pl.polsl.reservations.client;

import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.user.UserFacade;
import pl.polsl.reservations.userManagement.UserManagementFacade;
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
        UserManagementFacade userManagementFacadeRemote = 
                (UserManagementFacade)l.getRemote("UserManagementFacade");
        UserFacade userFacadeRemote = (UserFacade) l.getRemote("UserFacade");
        RoomManagementFacade roomManagementFacade = 
                (RoomManagementFacade) l.getRemote("RoomManagementFacade");
        ScheduleFacade scheduleFacadeRemote = (ScheduleFacade) l.getRemote("ScheduleFacade");
        
        //uzycie dowolnej metody zdalnej:
        Long privilige = userFacadeRemote.getUserPrivilege();
        System.out.println("Privilige level: " + privilige);
    }
}
