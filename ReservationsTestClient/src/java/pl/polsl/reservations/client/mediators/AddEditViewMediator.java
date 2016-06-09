package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditView;
import pl.polsl.reservations.client.views.DayTableModel;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.renderers.DayCustomRenderer;
import pl.polsl.reservations.client.views.utils.DateUtils;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.RoomTypesDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author Paweu
 */
public class AddEditViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private final UserFacade userFacade;
    private AddEditView addEditView;
    private Calendar date;
    private HashMap<Color, List<Integer>> reservationCellsRendererMap;
    private Integer roomNumber;

    private List<Integer> startQuarters;
    private List<Integer> endQuarters;

    public AddEditViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        date = null;

    }

    public AddEditViewMediator(Calendar date, Integer roomNumber) {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        this.date = date;

        this.roomNumber = roomNumber;
    }

    //Data do widoku przekazywana jest tak ze dla wejscia z tabeli bieze i ustawia kalendarz
    //a dla wejscia z File -> add, data jest brana ze statycznego datepickera
    public AddEditView createView(MainView parent) {
        addEditView = new AddEditView(parent, this, false);
        if (date != null) {
            setSelectedDate();
        } else {
            date = addEditView.getDatepicker().getDate();
        }
        getRooms();
        getReservations();
        setWorkersData();
        setTargetData();

        return addEditView;
    }

    public void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        List<Integer> floors = new ArrayList<>();
        for (RoomDTO room : roomsList) {
            Integer roomNumber = room.getNumber();
            Integer floor = roomNumber / 100;
            Integer number = roomNumber % 100;
            if (numbersMap.containsKey(floor)) {
                List<Integer> numbers = numbersMap.get(floor);
                numbers.add(number);
                numbersMap.put(floor, numbers);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(number);
                numbersMap.put(floor, numbers);
                floors.add(floor);
            }
        }
        addEditView.getRoomCb().setFloors(floors);
        for (Map.Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            addEditView.getRoomCb().setRooms(floorEntry.getValue(), floorEntry.getKey());
        }

        Integer floor = roomNumber / 100;
        Integer number = roomNumber % 100;
        addEditView.getRoomCb().selectItem(floor, number);
    }

    private boolean checkIfReservationAvaliable(int startTime, int endTime) {
        Calendar calendar = date;
        List<ReservationDTO> roomSchedule
                = scheduleFacade.getDetailedRoomSchedule(roomNumber, calendar.get(Calendar.YEAR),
                        DateUtils.getWeekOfSemester(date), DateUtils.getSemesterFromDate(date));
        for (ReservationDTO room : roomSchedule) {
            Integer roomStart = room.getStartTime();
            Integer roomEnd = room.getEndTime();

            if (startTime < roomStart && endTime > roomStart) {
                return false;
            }
            if (startTime < roomEnd && endTime > roomEnd) {
                return false;
            }
            if (startTime >= roomStart && endTime <= roomEnd) {
                return false;
            }

        }
        return true;
    }

    public boolean addReservation() {
        Integer roomID = roomManagementFacade.getRoom((Integer) addEditView.getRoomCb().getSelectedItem()).getId().intValue();
        Integer weekDay = date.get(Calendar.DAY_OF_WEEK);
        if (weekDay == 1) {
            weekDay = 6;
        } else {
            weekDay -= 2;
        }
        Integer startTime = getStartHourFromView() + weekDay * 96;
        Integer endTime = getEndHourFromView() + weekDay * 96;

        if (startTime < endTime) {
            if (checkIfReservationAvaliable(startTime, endTime)) {

                Calendar calendar = date;
                Integer typeId = getType().getId();
                Integer userId = getWorkersData().getId().intValue();
                //scheduleFacade.createReservation(roomID, startTime, endTime, DateUtils.getWeekOfSemester(date), date.get(Calendar.YEAR), DateUtils.getSemesterFromDate(date), typeId, userId);

                getReservations();
                return true;
            } else {
                JOptionPane.showMessageDialog(addEditView, "There is a reservation in selected time", "ERROR", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(addEditView, "Start date after end date", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void getReservations() {
        reservationCellsRendererMap = new HashMap<>();
        startQuarters = new ArrayList<>();
        endQuarters = new ArrayList<>();

        Calendar calendar = date;
        List<ReservationDTO> roomSchedule
                = scheduleFacade.getDetailedRoomSchedule(roomNumber, calendar.get(Calendar.YEAR),
                        DateUtils.getWeekOfSemester(date), DateUtils.getSemesterFromDate(date));

        addEditView.getDayTable().setModel(fillTable(roomSchedule));
    }

    private DefaultTableModel fillTable(List<ReservationDTO> roomSchedule) {
        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);

        for (ReservationDTO reservation : roomSchedule) {
            int endDay = reservation.getEndTime() / 96;
            int startDay = reservation.getStartTime() / 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96;
            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                dayOfWeek = 7;
            } else {
                dayOfWeek -= 2;
            }
            if (startDay == (int) dayOfWeek) {
                startQuarters.add(numberOfStartQuarter);
                if (numberOfEndQuarter < numberOfStartQuarter) {
                    endQuarters.add(95);
                } else {
                    endQuarters.add(numberOfEndQuarter);
                }
                defaultTableModel.setValueAt(reservation.getType(), numberOfStartQuarter % 32, numberOfStartQuarter / 32);
                createReservationsRendererList(numberOfStartQuarter, numberOfEndQuarter, reservation);
                UserDTO userDetails = userManagementFacade.getUserDetails(reservation.getUserId().intValue());
                String userString = userDetails.getName() + " " + userDetails.getSurname();
                defaultTableModel.setValueAt(userString, numberOfStartQuarter % 32 + 1, numberOfStartQuarter / 32);
            }

        }
        addEditView.getDayTable().setModel(defaultTableModel);
        addEditView.getDayTable().setDefaultRenderer(Object.class,
                new DayCustomRenderer(reservationCellsRendererMap, startQuarters, endQuarters));
        return defaultTableModel;
    }

    private void createReservationsRendererList(Integer numberOfStartQuarter,
            Integer numberOfEndQuarter, ReservationDTO reservation) {
        Color color = getColor(reservation);
        if (color != null) {
            for (Integer i = numberOfStartQuarter; i <= numberOfEndQuarter; i++) {
                if (reservationCellsRendererMap.containsKey(color)) {
                    List<Integer> reservationNumbers = reservationCellsRendererMap.get(color);
                    reservationNumbers.add(i);
                    reservationCellsRendererMap.put(color, reservationNumbers);
                } else {
                    List<Integer> reservationNumbers = new ArrayList<>();
                    reservationNumbers.add(i);
                    reservationCellsRendererMap.put(color, reservationNumbers);
                }
            }
        }
    }

    private Color getColor(ReservationDTO reservation) {
        Color color = null;
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        for (ReservationTypeDTO reservationType : reservationTypes) {
            if (reservationType.getShortDescription().equals(reservation.getType())) {
                try {
                    Field field = Color.class.getField(reservationType.getReservationColor());
                    color = (Color) field.get(null);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    color = null;
                }
            }
        }
        return color;
    }

    public Integer getStartHourFromView() {
        String selectedHour = (String) addEditView.getHourStartCb().getSelectedItem();
        for (int i = 0; i < 96; i++) {
            Integer hour = i / 4;
            Integer quarter = (i % 4) * 15;
            String hourString = hour.toString() + ":";
            if (quarter == 0) {
                hourString += "00";
            } else {
                hourString += quarter.toString();
            }
            if (selectedHour.equals(hourString)) {
                return i;
            }
        }
        return null;
    }

    public Integer getEndHourFromView() {
        String selectedHour = (String) addEditView.getHourStopCb().getSelectedItem();

        for (int i = 0; i < 96; i++) {
            Integer hour = i / 4;
            Integer quarter = (i % 4) * 15;
            String hourString = hour.toString() + ":";
            if (quarter == 0) {
                hourString += "00";
            } else {
                hourString += quarter.toString();
            }
            if (selectedHour.equals(hourString)) {
                return i;
            }
        }
        return null;
    }

    public UserDTO getWorkersData() {
        int selectedIndex = addEditView.getTeacherCb().getSelectedIndex();
        List<UserDTO> userDetailsList = userFacade.getUsersWithLowerPrivilegeLevel();
        UserDTO selectedUser = userDetailsList.get(selectedIndex);
        return selectedUser;
    }

    public void setWorkersData() {
        UserDTO currentUserDetails = userManagementFacade.getUserDetails(ClientContext.getUsername());
        List<UserDTO> userDetailsList = userFacade.getUsersWithLowerPrivilegeLevel();
        userDetailsList.stream().forEach((userDTO) -> {
            addEditView.getTeacherCb().addItem(userDTO.getName() + " " + userDTO.getSurname());
        });
        String name = currentUserDetails.getName() + " " + currentUserDetails.getSurname();
        addEditView.getTeacherCb().addItem(name);
        addEditView.getTeacherCb().setSelectedItem(name);
    }

    public RoomTypesDTO getType() {
        Integer selectedIndex = addEditView.getGroupCb().getSelectedIndex();
        List<RoomTypesDTO> roomTypes = roomManagementFacade.getRoomTypes();
        return roomTypes.get(selectedIndex);
    }

    public void setTargetData() {
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        for (ReservationTypeDTO reservationTypeDTO : reservationTypes) {
            addEditView.getGroupCb().addItem(reservationTypeDTO.getShortDescription());
        }
    }

    private void setSelectedDate() {
        addEditView.getDatepicker().setDate(date);
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

}
