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
import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.RoomDTO;

/**
 *
 * @author matis
 */
public class SingleRoomEquipmentReportDocument extends PDFDocument {

    public static final Font TITLE_BOLD_UNDERLINED
            = new Font(TimesRomanBase, 18, Font.BOLD | Font.UNDERLINE);
    public static final Font BOLD
            = new Font(TimesRomanBase, 12, Font.BOLD);
    public static final Font NORMAL
            = new Font(TimesRomanBase, 12);

    private RoomDTO roomDTO;
    private List<EquipmentDTO> equipmentOfRoom;

    public SingleRoomEquipmentReportDocument() {
        super();
        roomDTO = null;
        equipmentOfRoom = null;
    }
    
    public SingleRoomEquipmentReportDocument(String pathToResultFile, Rectangle pageSize,
            RoomDTO roomDTO, List<EquipmentDTO> equipmentOfRoom){
        super(pathToResultFile, pageSize);
        this.roomDTO = roomDTO;
        this.equipmentOfRoom = equipmentOfRoom;
    }

    @Override
    public void generateHeader() {
        Paragraph header = new Paragraph();
        Phrase title = new Phrase("Room inventory report", TITLE_BOLD_UNDERLINED);
        header.add(title);
        header.add(Chunk.NEWLINE);
        header.add(Chunk.NEWLINE);
        if (roomDTO != null) {
            if (roomDTO.getDepartment() != null) {
                String departamentString = "Departament: " + roomDTO.getDepartment();
                Phrase departament = new Phrase(departamentString, BOLD);
                header.add(departament);
                header.add(Chunk.NEWLINE);
            }
            Integer number = roomDTO.getNumber();
            String roomNumberString = "Room number: " + number.toString();
            Phrase roomNumber = new Phrase(roomNumberString, BOLD);
            header.add(roomNumber);
            header.add(Chunk.NEWLINE);
            if (roomDTO.getKeeperId().toString() != null) {
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
                String numberOfSeatsString = "Number of seats: " + roomDTO.getNumberOfSeats().toString();
                Phrase numberOfSeats = new Phrase(numberOfSeatsString, BOLD);
                header.add(numberOfSeats);
                header.add(Chunk.NEWLINE);
            }
        }
        try {
            this.document.add(header);
        } catch (DocumentException ex) {
            Logger.getLogger(SingleRoomEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void generateContent() {
        try {
            document.add(Chunk.NEWLINE);
            document.add(generateContentTable());
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SingleRoomEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable generateContentTable() {
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
        for (EquipmentDTO equipmentDTO : equipmentOfRoom) {
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
     * @return the roomDTO
     */
    public RoomDTO getRoomDTO() {
        return roomDTO;
    }

    /**
     * @param roomDTO the roomDTO to set
     */
    public void setRoomDTO(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }

    /**
     * @return the equipmentOfRoom
     */
    public List<EquipmentDTO> getEquipmentOfRoom() {
        return equipmentOfRoom;
    }

    /**
     * @param equipmentOfRoom the equipmentOfRoom to set
     */
    public void setEquipmentOfRoom(List<EquipmentDTO> equipmentOfRoom) {
        this.equipmentOfRoom = equipmentOfRoom;
    }

}
