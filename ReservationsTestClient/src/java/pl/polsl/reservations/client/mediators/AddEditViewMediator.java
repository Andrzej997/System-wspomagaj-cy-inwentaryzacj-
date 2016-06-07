package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditView;
import pl.polsl.reservations.client.views.DayTableModel;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.renderers.DayCustomRenderer;
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
        roomsList.stream().forEach((room) -> {
            addEditView.getRoomCb().addItem(room.getNumber());
            if (roomNumber != null && roomNumber.equals(room.getNumber())) {
                addEditView.getRoomCb().setSelectedItem(room.getNumber());
            }
        });
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

        Calendar calendar = date;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfSemester = 1;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        boolean semester = true;

        if (month >= 10 || month <= 2) {
            semester = false;
            Calendar cal = calendar;
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            cal.set(Calendar.DATE, 1);

            if (month >= 10 && month <= 12) {
                weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
            } else {   //sprawdziæ zachowanie jak sylwester nie jest w niedzielê
                Calendar calPom = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.YEAR, calPom.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.MONTH, Calendar.DECEMBER);
                calPom.set(Calendar.DATE, 31);

                weekOfSemester = calPom.get(Calendar.WEEK_OF_YEAR) - cal.get(Calendar.WEEK_OF_YEAR);
                calPom.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
                weekOfSemester += calendar.get(Calendar.WEEK_OF_YEAR) - calPom.get(Calendar.WEEK_OF_YEAR);
            }

        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.MARCH);
            cal.set(Calendar.DATE, 1);

            weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
        }

        Integer typeId = getType().getId();

        Integer userId = getWorkersData().getId().intValue();

        scheduleFacade.createReservation(roomID, startTime, endTime, weekOfSemester, date.get(Calendar.YEAR), semester, typeId, userId);

        getReservations();

        return true;
    }

    public void getReservations() {
        reservationCellsRendererMap = new HashMap<>();
        startQuarters = new ArrayList<>();
        endQuarters = new ArrayList<>();

        Calendar calendar = date;
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int weekOfSemester = 1;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        boolean semester = true;

        if (month >= 10 || month <= 2) {
            semester = false;
            Calendar cal = calendar;
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            cal.set(Calendar.DATE, 1);

            if (month >= 10 && month <= 12) {
                weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
            } else {   //sprawdziæ zachowanie jak sylwester nie jest w niedzielê
                Calendar calPom = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.YEAR, calPom.get(Calendar.YEAR) - 1);
                calPom.set(Calendar.MONTH, Calendar.DECEMBER);
                calPom.set(Calendar.DATE, 31);

                weekOfSemester = calPom.get(Calendar.WEEK_OF_YEAR) - cal.get(Calendar.WEEK_OF_YEAR);
                calPom.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
                weekOfSemester += calendar.get(Calendar.WEEK_OF_YEAR) - calPom.get(Calendar.WEEK_OF_YEAR);
            }

        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, Calendar.MARCH);
            cal.set(Calendar.DATE, 1);

            weekOfSemester = weekOfYear - cal.get(Calendar.WEEK_OF_YEAR) + 1;
        }

        List<ReservationDTO> roomSchedule
                // = scheduleFacade.getRoomSchedule(chooseRoomDropdown.getSelectedItem(), calendar.get(Calendar.YEAR), semester);
                = scheduleFacade.getDetailedRoomSchedule(roomNumber, calendar.get(Calendar.YEAR), weekOfSemester, semester);

        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);

        for (ReservationDTO reservation : roomSchedule) {
            int endDay = reservation.getEndTime() / 96;
            int startDay = reservation.getStartTime() / 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96;
            if (numberOfEndQuarter == 39) {
                break;
            }
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
            addEditView.getDayTable().setModel(defaultTableModel);
            addEditView.getDayTable().setDefaultRenderer(Object.class,
                    new DayCustomRenderer(reservationCellsRendererMap, startQuarters, endQuarters));
        }

        addEditView.getDayTable().setModel(defaultTableModel);
    }

    private void createReservationsRendererList(Integer numberOfStartQuarter,
            Integer numberOfEndQuarter, ReservationDTO reservation) {
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

}
