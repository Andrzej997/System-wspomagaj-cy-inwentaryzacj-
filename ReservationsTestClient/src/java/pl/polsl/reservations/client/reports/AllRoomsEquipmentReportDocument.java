package pl.polsl.reservations.client.reports;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;

/**
 *
 * @author matis
 */
public class AllRoomsEquipmentReportDocument extends PDFDocument {

    
    public static final Font TITLE_BOLD_UNDERLINED
            = new Font(TimesRomanBase, 20, Font.BOLD | Font.UNDERLINE);
    public static final Font BOLD
            = new Font(TimesRomanBase, 12, Font.BOLD);
    public static final Font NORMAL
            = new Font(TimesRomanBase, 12);

    private List<RoomDTO> roomDTOCollection;

    private final RoomManagementFacade roomManagementFacade
            = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");

    public AllRoomsEquipmentReportDocument() {
        super();
        roomDTOCollection = null;
    }

    public AllRoomsEquipmentReportDocument(List<RoomDTO> roomDTOCollection, String pathToResultFile, Rectangle pageSize){
        super(pathToResultFile, pageSize);
        this.roomDTOCollection = roomDTOCollection;
    }
    
    @Override
    public void generateHeader() {
        Paragraph header = new Paragraph();
        Phrase title = new Phrase("All rooms inventory report", TITLE_BOLD_UNDERLINED);
        header.add(title);
        header.add(Chunk.NEWLINE);
        try {
            document.add(header);
        } catch (DocumentException ex) {
            Logger.getLogger(AllRoomsEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void generateContent() {
        for (RoomDTO room : roomDTOCollection) {
            Long id = room.getId();
            List<EquipmentDTO> roomEquipment = roomManagementFacade.getRoomEquipment(id.intValue());
            try {
                document.add(Chunk.NEWLINE);
                document.add(generateTableHeader(room));
                document.add(Chunk.NEWLINE);
                document.add(generateContentTable(roomEquipment));
                document.add(Chunk.NEWLINE);
            } catch (DocumentException ex) {
                Logger.getLogger(AllRoomsEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Paragraph generateTableHeader(RoomDTO roomDTO) {
        Paragraph header = new Paragraph();
        if (roomDTO.getDepartment() != null) {
            String departamentString = "Departament: " + roomDTO.getDepartment();
            Phrase departament = new Phrase(departamentString, BOLD);
            header.add(departament);
            header.add(Chunk.NEWLINE);
        }
        String roomNumberString = "Room number: " + roomDTO.getNumber();
        Phrase roomNumber = new Phrase(roomNumberString, BOLD);
        header.add(roomNumber);
        header.add(Chunk.NEWLINE);
        if (roomDTO.getKeeperId() != null) {
            String roomKeeperString = "Room keeper: " + roomDTO.getKeeperId().toString();
            Phrase roomKeeper = new Phrase(roomKeeperString, BOLD);
            header.add(roomKeeper);
            header.add(Chunk.NEWLINE);
        }
        if (roomDTO.getType() != null) {
            String roomTypeString = "Room type: " + roomDTO.getType();
            Phrase roomType = new Phrase(roomTypeString, BOLD);
            header.add(roomType);
            header.add(Chunk.NEWLINE);
        }
        if (roomDTO.getNumberOfSeats() != null) {
            String numberOfSeatsString = "Number of seats: " + roomDTO.getNumberOfSeats();
            Phrase numberOfSeats = new Phrase(numberOfSeatsString, BOLD);
            header.add(numberOfSeats);
            header.add(Chunk.NEWLINE);
        }
        return header;
    }

    public PdfPTable generateContentTable(List<EquipmentDTO> roomEquipment) {
        PdfPTable contentTable = new PdfPTable(4);
        PdfPCell cell;
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Equipment name", NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Quantity", NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Type", NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("State", NORMAL));
        contentTable.addCell(cell);
        for (EquipmentDTO equipmentDTO : roomEquipment) {
            cell = new PdfPCell(new Phrase(equipmentDTO.getName()));
            contentTable.addCell(cell);
            cell = new PdfPCell(new Phrase(equipmentDTO.getQuantity().toString()));
            contentTable.addCell(cell);
            cell = new PdfPCell(new Phrase(equipmentDTO.getType()));
            contentTable.addCell(cell);
            cell = new PdfPCell(new Phrase(equipmentDTO.getState()));
            contentTable.addCell(cell);
        }
        return contentTable;
    }

    /**
     * @return the roomDTOCollection
     */
    public List<RoomDTO> getRoomDTOCollection() {
        return roomDTOCollection;
    }

    /**
     * @param roomDTOCollection the roomDTOCollection to set
     */
    public void setRoomDTOCollection(List<RoomDTO> roomDTOCollection) {
        this.roomDTOCollection = roomDTOCollection;
    }

}
