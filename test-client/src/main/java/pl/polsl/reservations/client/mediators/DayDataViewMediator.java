package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.DayDataView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.renderers.DayCustomRenderer;
import pl.polsl.reservations.client.views.utils.DateUtils;
import pl.polsl.reservations.client.views.utils.DayTableModel;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author Pawe�
 */
public class DayDataViewMediator {

    private final ScheduleFacade scheduleFacade = Lookup.getScheduleFacade();
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();
    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private Calendar date;
    private DayDataView dayDataView;
    private HashMap<Color, List<Integer>> reservationCellsRendererMap;
    private List<Integer> startQuarters;
    private List<Integer> endQuarters;
    private Integer roomNumber;

    public DayDataViewMediator() {
        reservationCellsRendererMap = new HashMap<>();
    }

    public DayDataViewMediator(Integer roomNumber) {
        reservationCellsRendererMap = new HashMap<>();
        this.roomNumber = roomNumber;
    }

    public DayDataView createView(MainView parent, Calendar date) {
        dayDataView = new DayDataView(parent, date, this);
        this.date = date;
        getRooms();
        getReservations();
        return dayDataView;
    }

    public void getReservations() {
        reservationCellsRendererMap = new HashMap<>();
        startQuarters = new ArrayList<>();
        endQuarters = new ArrayList<>();

        Calendar calendar = date;
        List<ReservationDTO> roomSchedule = scheduleFacade.getDetailedRoomSchedule(
                dayDataView.getChooseRoomDropdown().getSelectedItem(),
                calendar.get(Calendar.YEAR), DateUtils.getWeekOfSemester(date),
                DateUtils.getSemesterFromDate(date));

        dayDataView.getPlanView().setModel(fillTable(roomSchedule));
    }

    private DefaultTableModel fillTable(List<ReservationDTO> roomSchedule) {
        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);
        for (ReservationDTO reservation : roomSchedule) {
            int startDay = reservation.getStartTime() / 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96;
            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                dayOfWeek = 6;
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
        dayDataView.getPlanView().setModel(defaultTableModel);
        dayDataView.getPlanView().setDefaultRenderer(Object.class,
                new DayCustomRenderer(reservationCellsRendererMap, startQuarters, endQuarters));

        return defaultTableModel;
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
        dayDataView.getChooseRoomDropdown().setFloors(floors);
        for (Map.Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            dayDataView.getChooseRoomDropdown().setRooms(floorEntry.getValue(), floorEntry.getKey());
        }

        if (roomNumber != null) {
            Integer floor = roomNumber / 100;
            Integer number = roomNumber % 100;
            dayDataView.getChooseRoomDropdown().selectItem(floor, number);
        } else {
            Set<Integer> keySet = numbersMap.keySet();
            Object[] keyArray = keySet.toArray();
            dayDataView.getChooseRoomDropdown().selectItem((Integer) keyArray[0],
                    numbersMap.get((Integer) keyArray[0]).get(0));
        }
    }

    private void createReservationsRendererList(Integer numberOfStartQuarter,
            Integer numberOfEndQuarter, ReservationDTO reservation) {
        Color color = getColorData(reservation);
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

    private Color getColorData(ReservationDTO reservation) {
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

}
