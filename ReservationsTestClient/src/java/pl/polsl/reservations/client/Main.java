package pl.polsl.reservations.client;

import com.itextpdf.text.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import javax.swing.Painter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import pl.polsl.reservations.client.mediators.MainViewMediator;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;

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
                    setStyle();
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

    private static void setStyle() {
        Painter<Component> p = new Painter<Component>() {
            public void paint(Graphics2D g, Component c, int width, int height) {
                g.setColor(new Color(247, 248, 250));
        }
        };
        UIManager.getLookAndFeelDefaults().put("InternalFrameTitlePane.background", new Color(230, 230, 250));
        UIManager.getLookAndFeelDefaults().put("background", new Color(230, 230, 250));
        UIManager.getLookAndFeelDefaults().put("Table.font",
                new javax.swing.plaf.FontUIResource("Arial", Font.NORMAL, 13));
        UIManager.getLookAndFeelDefaults().put("TableHeader.foreground", new Color(255, 255, 255));
        UIManager.getLookAndFeelDefaults().put("defaultFont",
                new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 13));
        UIManager.getLookAndFeelDefaults().put("ComboBox.foreground", new Color(247, 248, 250));
        
        UIManager.getLookAndFeelDefaults().put("TextField.background", new Color(247, 248, 250));
        UIManager.getLookAndFeelDefaults().put("FormattedTextField.background", new Color(247, 248, 250));
        UIManager.getLookAndFeelDefaults().put("PasswordField.background", new Color(247, 248, 250));
        UIManager.getLookAndFeelDefaults().put("Button.foreground", new Color(247, 248, 250));
        UIManager.getLookAndFeelDefaults().put("InternalFrame.background", new Color(247, 248, 250));

    }
}
