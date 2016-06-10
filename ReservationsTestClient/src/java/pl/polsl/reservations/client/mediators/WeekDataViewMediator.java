package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.client.views.utils.RoomComboBox;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.client.views.renderers.WeekCustomRenderer;
import pl.polsl.reservations.client.views.utils.DateUtils;
import pl.polsl.reservations.client.views.utils.Pair;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author Pawe�
 */
public class WeekDataViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private WeekDataView weekDataView;
    private Integer selectedItem;
    private HashMap<Color, List<Integer>> reservationCellsRendererMap;

    private List<Pair<Integer, Integer>> startQuarters;
    private List<Pair<Integer, Integer>> endQuarters;

    public WeekDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        reservationCellsRendererMap = new HashMap<>();
    }

    public WeekDataView createView(MainView parent, Object selectedItem) {
        weekDataView = new WeekDataView(parent, selectedItem, this);
        if (selectedItem instanceof Integer) {
            this.selectedItem = (Integer) selectedItem;
        }
        getRooms();
        getReservations();
        return weekDataView;
    }

    public boolean ifOnReservation(Integer row, Integer column) {
        Calendar calendar = weekDataView.getStartDate();
        List<ReservationDTO> roomSchedule
                = scheduleFacade.getDetailedRoomSchedule(weekDataView.getChooseRoomDropdown().getSelectedItem(),
                        calendar.get(Calendar.YEAR), DateUtils.getWeekOfSemester(calendar), DateUtils.getSemesterFromDate(calendar));

        Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek = 6;
        } else {
            dayOfWeek -= 2;
        }
        Integer selectedTime =  (column-1) * 96 + row+32;

        for (ReservationDTO reservation : roomSchedule) {
            Integer startTime = reservation.getStartTime();
            Integer endTime = reservation.getEndTime();

            if (selectedTime >= startTime && selectedTime <= endTime) {
                return true;
            }

        }
        return false;
    }

    public void getReservations() {

        startQuarters = new ArrayList<>();
        endQuarters = new ArrayList<>();
        reservationCellsRendererMap = new HashMap<>();

        Calendar calendar = weekDataView.getStartDate();
        RoomComboBox chooseRoomDropdown = (RoomComboBox) weekDataView.getChooseRoomDropdown();
        List<ReservationDTO> roomSchedule
                = scheduleFacade.getDetailedRoomSchedule(chooseRoomDropdown.getSelectedItem(),
                        calendar.get(Calendar.YEAR), DateUtils.getWeekOfSemester(calendar), DateUtils.getSemesterFromDate(calendar));

        weekDataView.getPlanTable().setModel(fillTable(roomSchedule));

        weekDataView.getPlanTable().setDefaultRenderer(Object.class, new WeekCustomRenderer(reservationCellsRendererMap, startQuarters, endQuarters));

    }

    private DefaultTableModel fillTable(List<ReservationDTO> roomSchedule) {
        DefaultTableModel defaultTableModel = new DefaultTableModelImpl(32, 8);

        roomSchedule.stream().forEach((reservation) -> {
            int endDay = reservation.getEndTime() / 96;
            int startDay = reservation.getStartTime() / 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96 - 32; //r�znica mi�dzy godzinami w bazie i tabeli
            int numberOfStartQuarter = reservation.getStartTime() % 96 - 32;
            if (!(numberOfEndQuarter > 31 || numberOfEndQuarter < 0)) {
                if (!(numberOfStartQuarter > 31 || numberOfStartQuarter < 0)) {
                    Pair startEntry = new Pair(startDay, numberOfStartQuarter);
                    Pair endEntry = new Pair(endDay, numberOfEndQuarter);

                    startQuarters.add(startEntry);
                    endQuarters.add(endEntry);

                    defaultTableModel.setValueAt(reservation.getType(), numberOfStartQuarter, startDay + 1);
                    defaultTableModel.setValueAt(userManagementFacade.getUserDetails(reservation.getUserId().intValue()).getName() + " " + userManagementFacade.getUserDetails(reservation.getUserId().intValue()).getSurname(), numberOfStartQuarter + 1, startDay + 1);

                    createReservationsRendererList(endDay, startDay,
                            numberOfStartQuarter, numberOfEndQuarter, reservation);
                }
            }
        });
        return defaultTableModel;
    }

    public void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        RoomComboBox roomComboBox = (RoomComboBox) weekDataView.getChooseRoomDropdown();
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
        roomComboBox.setFloors(floors);
        for (Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            roomComboBox.setRooms(floorEntry.getValue(), floorEntry.getKey());
        }

        Integer floor = selectedItem / 100;
        Integer number = selectedItem % 100;
        roomComboBox.selectItem(floor, number);
    }

    private void createReservationsRendererList(Integer endDay, Integer startDay,
            Integer numberOfStartQuarter, Integer numberOfEndQuarter, ReservationDTO reservation) {
        if (!Objects.equals(startDay, endDay)) {
            return;
        }
        Color color = getColorData(reservation);
        if (color != null) {
            for (Integer i = numberOfStartQuarter; i <= numberOfEndQuarter; i++) {
                if (reservationCellsRendererMap.containsKey(color)) {
                    List<Integer> reservationNumbers = reservationCellsRendererMap.get(color);
                    reservationNumbers.add(i + startDay * 32);
                    reservationCellsRendererMap.put(color, reservationNumbers);
                } else {
                    List<Integer> reservationNumbers = new ArrayList<>();
                    reservationNumbers.add(i + startDay * 32);
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

    private static class DefaultTableModelImpl extends DefaultTableModel {

        private static final long serialVersionUID = 1325718817963973431L;

        String[] days = new String[]{"Hours:", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        public DefaultTableModelImpl(int rowCount, int columnCount) {
            super(rowCount, columnCount);
            super.setColumnIdentifiers(days);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
