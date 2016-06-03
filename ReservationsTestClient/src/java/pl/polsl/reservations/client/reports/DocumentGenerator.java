package pl.polsl.reservations.client.reports;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class DocumentGenerator {

    UserManagementFacade userManagementFacadeRemote
            = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
    UserFacade userFacadeRemote = (UserFacade) Lookup.getRemote("UserFacade");
    RoomManagementFacade roomManagementFacade
            = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    ScheduleFacade scheduleFacadeRemote = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");

    public final String pathToFile;

    public DocumentGenerator() {
        pathToFile = null;
    }

    public DocumentGenerator(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public void generateAllRoomsEquipmentReport() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        AllRoomsEquipmentReportDocument reportDocument
                = new AllRoomsEquipmentReportDocument(roomsList, pathToFile, PageSize.A4);
        try {
            reportDocument.generatePDF();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        runDocumentReader();
    }

    public void generateDepartamentRoomsEquipmentReport(String departamentName) {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        List<RoomDTO> departamentRoomsList = new ArrayList<>();
        for (RoomDTO roomDTO : roomsList) {
            if (roomDTO.getDepartment().equals(departamentName)) {
                departamentRoomsList.add(roomDTO);
            }
        }
        DepartamentRoomsEquipmentReportDocument reportDocument
                = new DepartamentRoomsEquipmentReportDocument(departamentRoomsList, departamentName, pathToFile, PageSize.A4);
        try {
            reportDocument.generatePDF();
        } catch (DocumentException | FileNotFoundException ex) {
            Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        runDocumentReader();
    }

    public void generateSingleRoomEquipmentReport(Integer roomNumber) {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        RoomDTO room = null;
        for (RoomDTO roomDTO : roomsList) {
            if (Objects.equals(roomDTO.getNumber(), roomNumber)) {
                room = roomDTO;
            }
        }
        if (room != null) {
            List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(room.getId().intValue());
            SingleRoomEquipmentReportDocument reportDocument
                    = new SingleRoomEquipmentReportDocument(pathToFile, PageSize.A4, room, roomEquipment);
        }
        runDocumentReader();
    }

    private void runDocumentReader() {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(pathToFile);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                Logger.getLogger(DocumentGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
