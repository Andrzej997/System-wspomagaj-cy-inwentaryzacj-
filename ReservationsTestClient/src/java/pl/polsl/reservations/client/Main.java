package pl.polsl.reservations.client;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pl.polsl.reservations.client.mediators.MainViewMediator;
import pl.polsl.reservations.client.views.MainView;

/**
 *
 * @author matis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {

        //pobranie lookupem dostępnych dla klienta fasad
        //generalnie pobierajcie wedle potrzeb tu macie przykład jak pobrać wszystkie
        UserManagementFacade userManagementFacadeRemote
                = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        UserFacade userFacadeRemote = (UserFacade) Lookup.getRemote("UserFacade");
        RoomManagementFacade roomManagementFacade
                = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        ScheduleFacade scheduleFacadeRemote = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");

        //uzycie dowolnej metody zdalnej:
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        SingleRoomEquipmentReportDocument report;
        try {
            for (RoomDTO room : roomsList) {
                if (room.getId() == 1) {
                    Long id = room.getId();
                    List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(id.intValue());
                    report = new SingleRoomEquipmentReportDocument("D:\\test.pdf", new RectangleReadOnly(595, 842), room, roomEquipment);
                    report.generatePDF();
                }
            }
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            MainViewMediator mainViewMediator = new MainViewMediator();
            MainView window = new MainView(mainViewMediator);
            mainViewMediator.createView();
        });
    }
}
