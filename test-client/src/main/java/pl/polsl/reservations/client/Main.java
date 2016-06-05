package pl.polsl.reservations.client;

import java.util.List;

import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UnauthorizedAccessException;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        
        //pobranie lookupem dostępnych dla klienta fasad
        //generalnie pobierajcie wedle potrzeb tu macie przykład jak pobrać wszystkie
        UserManagementFacade userManagementFacadeRemote =
                (UserManagementFacade)Lookup.getRemote("UserManagementFacade");
        UserFacade userFacadeRemote = (UserFacade) Lookup.getRemote("UserFacade");
        RoomManagementFacade roomManagementFacade =
                (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        ScheduleFacade scheduleFacadeRemote = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        
        //uzycie dowolnej metody zdalnej:
        //Long privilige = userFacadeRemote.getUserPrivilege();
//        System.out.println("Privilige level: " + privilige);

//        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        userFacadeRemote.login("m1@onet.pl", "123");
        List<RoomDTO> roomsList2 = roomManagementFacade.getRoomsList();

        List<EquipmentDTO> res = roomManagementFacade.getDepartmentEquipment(1);
        try {
            roomManagementFacade.addEquipment(6, "asdasdasd", 3, (short)1, (short)1);
            roomManagementFacade.moveEquipment(1, 2);
        } catch (UnauthorizedAccessException e) {
            e.printStackTrace();
        }

    }
//
//    public static void main(String args[]) {
//        try {
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        java.awt.EventQueue.invokeLater(() -> {
//            MainViewMediator mainViewMediator = new MainViewMediator();
//            MainView window = new MainView(mainViewMediator);
//            mainViewMediator.createView();
//        });
//    }
}
