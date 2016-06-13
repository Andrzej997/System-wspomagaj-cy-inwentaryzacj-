package pl.polsl.reservations.client.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.utils.DateUtils;
import pl.polsl.reservations.client.views.utils.FontUtils;
import pl.polsl.reservations.client.views.utils.Pair;
import pl.polsl.reservations.dto.EquipmentDTO;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class SingleRoomEquipmentReportDocument extends PDFDocument {

    private RoomDTO roomDTO;
    private List<EquipmentDTO> equipmentOfRoom;
    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();
    private final ScheduleFacade scheduleFacade = Lookup.getScheduleFacade();
    private final List<ReservationTypeDTO> reservationTypes;

    public SingleRoomEquipmentReportDocument() {
        super();
        roomDTO = null;
        equipmentOfRoom = null;
        reservationTypes = scheduleFacade.getReservationTypes();
    }

    public SingleRoomEquipmentReportDocument(String pathToResultFile, Rectangle pageSize,
            RoomDTO roomDTO, List<EquipmentDTO> equipmentOfRoom) {
        super(pathToResultFile, pageSize);
        this.roomDTO = roomDTO;
        this.equipmentOfRoom = equipmentOfRoom;
        reservationTypes = scheduleFacade.getReservationTypes();
    }

    @Override
    public void generateHeader() {
        Paragraph header = new Paragraph();
        Phrase title = new Phrase("Room inventory report", FontUtils.TITLE_BOLD_UNDERLINED);
        header.add(title);
        header.add(Chunk.NEWLINE);
        header.add(Chunk.NEWLINE);
        if (roomDTO != null) {
            if (roomDTO.getDepartment() != null) {
                String departamentString = "Departament: " + roomDTO.getDepartment();
                Phrase departament = new Phrase(departamentString, FontUtils.BOLD);
                header.add(departament);
                header.add(Chunk.NEWLINE);
            }
            Integer number = roomDTO.getNumber();
            String roomNumberString = "Room number: " + number.toString();
            Phrase roomNumber = new Phrase(roomNumberString, FontUtils.BOLD);
            header.add(roomNumber);
            header.add(Chunk.NEWLINE);
            if (roomDTO.getKeeperId().toString() != null) {
                Long keeperId = roomDTO.getKeeperId();
                UserDTO userDetails = userManagementFacade.getUserDetails(keeperId.intValue());
                String roomKeeperString = "Room keeper: " + userDetails.getName() + " " + userDetails.getSurname();
                Phrase roomKeeper = new Phrase(roomKeeperString, FontUtils.BOLD);
                header.add(roomKeeper);
                header.add(Chunk.NEWLINE);
            }
            if (roomDTO.getType() != null) {
                String roomTypeString = "Room type: " + roomDTO.getType();
                Phrase roomType = new Phrase(roomTypeString, FontUtils.BOLD);
                header.add(roomType);
                header.add(Chunk.NEWLINE);
            }
            if (roomDTO.getNumberOfSeats() != null) {
                String numberOfSeatsString = "Number of seats: " + roomDTO.getNumberOfSeats().toString();
                Phrase numberOfSeats = new Phrase(numberOfSeatsString, FontUtils.BOLD);
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
            createWorkersList();
            document.newPage();
            document.add(Chunk.NEWLINE);
            generateScheduleHeader();
            document.add(generateScheduleTable());
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(SingleRoomEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable generateContentTable() {
        PdfPTable contentTable = new PdfPTable(4);
        PdfPCell cell;
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Equipment name", FontUtils.NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Quantity", FontUtils.NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Type", FontUtils.NORMAL));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("State", FontUtils.NORMAL));
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

    public void createWorkersList() {
        Long id = roomDTO.getId();
        List<UserDTO> roomWorkers = roomManagementFacade.getRoomWorkers(id);
        if(roomWorkers == null || roomWorkers.isEmpty()){
            return;
        }
        Paragraph paragraph = new Paragraph();
        Phrase title = new Phrase("Room workers: ", FontUtils.TITLE_BOLD_UNDERLINED);
        paragraph.add(title);
        paragraph.add(Chunk.NEWLINE);
        for (UserDTO user : roomWorkers) {
            String workerString = "Personal data: " + user.getName() + " " + user.getSurname();
            Phrase worker = new Phrase(workerString, FontUtils.NORMAL);
            String peselString = "Pesel: " + user.getPesel();
            Phrase pesel = new Phrase(peselString, FontUtils.NORMAL);
            String addressString = "Address: " + user.getAddress();
            String phoneNumber = "Phone: " + user.getPhoneNumber();
            paragraph.add(worker);
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(pesel);
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(addressString);
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(phoneNumber);
            paragraph.add(Chunk.NEWLINE);
        }
        try {
            document.add(paragraph);
        } catch (DocumentException ex) {
            Logger.getLogger(SingleRoomEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateScheduleHeader() {
        Paragraph paragraph = new Paragraph();
        Phrase title = new Phrase("Room schedule", FontUtils.BOLD);
        paragraph.add(title);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        try {
            document.add(paragraph);
        } catch (DocumentException ex) {
            Logger.getLogger(SingleRoomEquipmentReportDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PdfPTable generateScheduleTable() {
        PdfPTable contentTable = new PdfPTable(8);
        PdfPCell cell;
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Time", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Monday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Tuesday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Wednesday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Thursday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Friday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Saturday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        cell = new PdfPHeaderCell();
        cell.addElement(new Phrase("Sunday", FontUtils.SMALL_BOLD));
        contentTable.addCell(cell);
        Integer number = roomDTO.getNumber();
        Calendar curentDate = Calendar.getInstance();
        Boolean semesterFromDate = DateUtils.getSemesterFromDate(curentDate);
        Integer year = DateUtils.getYear(curentDate);
        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule(number, year, semesterFromDate);
        if (roomSchedule != null) {
            HashMap<Pair<Integer, Integer>, PdfPCell> paintedCells = createCellsCollection(roomSchedule);
            for (Integer i = 24; i < 72; i++) {
                for (Integer j = 0; j < 8; j++) {
                    if (j == 0 && i % 4 == 0) {
                        Integer hour = i / 4;
                        String hourString = hour.toString() + ":00";
                        cell = new PdfPCell(new Phrase(hourString, FontUtils.SMALL_BOLD));
                        cell.setBorderColor(BaseColor.GRAY);
                    } else if (j == 0 && i % 4 != 0) {
                        Integer hour = i / 4;
                        String hourString = hour.toString() + ":";
                        Integer quarter = (i % 4) * 15;
                        hourString += quarter.toString();
                        cell = new PdfPCell(new Phrase(hourString, FontUtils.SMALL));
                        cell.setBorderColor(BaseColor.GRAY);
                    } else {
                        Pair<Integer, Integer> address = new Pair(i, j - 1);
                        if (paintedCells.containsKey(address)) {
                            cell = paintedCells.get(address);
                        } else {
                            cell = new PdfPCell();
                        }
                    }
                    contentTable.addCell(cell);
                }
            }
        }
        return contentTable;
    }

    private HashMap<Pair<Integer, Integer>, PdfPCell> createCellsCollection(List<ReservationDTO> roomSchedule) {
        HashMap<Pair<Integer, Integer>, PdfPCell> cellsMap = new HashMap<>();
        for (ReservationDTO reservation : roomSchedule) {
            Integer column = reservation.getEndTime() / 96;
            Integer startTime = reservation.getStartTime() % 96;
            Integer endTime = reservation.getEndTime() % 96;
            if (startTime < 24 && endTime >= 25) {
                startTime = 24;
            }
            if (startTime < 70 && endTime > 71) {
                endTime = 71;
            }
            if (startTime < 24 || startTime > 72) {
                continue;
            }
            if (endTime < 24 || endTime > 72) {
                continue;
            }
            for (int k = startTime; k <= endTime; k++) {
                PdfPCell cell = createContentTable(reservation, startTime, endTime, k);
                if (k == startTime + 1) {
                    Long userId = reservation.getUserId();
                    UserDTO userDetails = userManagementFacade.getUserDetails(userId.intValue());
                    String userData = userDetails.getName() + " " + userDetails.getSurname();
                    cell.setPhrase(new Phrase(userData, FontUtils.SMALL));
                }
                Pair<Integer, Integer> address = new Pair(k, column);
                cellsMap.put(address, cell);
            }
        }
        return cellsMap;
    }

    private PdfPCell createContentTable(ReservationDTO reservation, Integer startTime, Integer endTime, Integer row) {
        PdfPCell cell = new PdfPCell(new Phrase("", FontUtils.SMALL));
        if (Objects.equals(row, startTime)) {
            cell = new PdfPCell(new Phrase(reservation.getType(), FontUtils.SMALL));
            cell.setBorder(Rectangle.TOP + Rectangle.LEFT + Rectangle.RIGHT);
        } else if (Objects.equals(row, endTime)) {
            cell = new PdfPCell();
            cell.setBorder(Rectangle.BOTTOM + Rectangle.LEFT + Rectangle.RIGHT);
        } else {
            cell = new PdfPCell();
            cell.setBorder(Rectangle.LEFT + Rectangle.RIGHT);
        }
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBackgroundColor(getColorData(reservation));
        return cell;
    }

    private BaseColor getColorData(ReservationDTO reservation) {
        BaseColor color = null;

        for (ReservationTypeDTO reservationType : reservationTypes) {
            if (reservationType.getShortDescription().equals(reservation.getType())) {
                try {
                    Field field = BaseColor.class
                            .getField(reservationType.getReservationColor());
                    color = (BaseColor) field.get(null);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    color = null;
                }
            }
        }
        return color;
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
