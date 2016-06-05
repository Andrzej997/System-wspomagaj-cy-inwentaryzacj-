package pl.polsl.reservations.client.mediators;

import java.awt.Color;
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
import pl.polsl.reservations.client.views.renderers.CustomRenderer;
import pl.polsl.reservations.dto.ReservationTypeDTO;

/**
 *
 * @author Paweï¿½
 */
public class WeekDataViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private WeekDataView weekDataView;
    private Integer selectedItem;
    private HashMap<Color, List<Integer>> reservationCellsRendererMap;

    public WeekDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        reservationCellsRendererMap = new HashMap<>();
    }

    public WeekDataView createView(MainView parent, Object selectedItem) {
        weekDataView = new WeekDataView(parent, selectedItem, this);

        if (selectedItem instanceof Integer) {
            this.selectedItem = (Integer) selectedItem;
        }

        getRooms();
        //   weekDataView.getChooseRoomDropdown()
        //           .setSelectedItem(weekDataView.getSelectedItem());

        getReservations();

        return weekDataView;
    }

    public void getReservations() {
        reservationCellsRendererMap = new HashMap<>();
     //   Date date = new Date();
        Calendar calendar = weekDataView.getStartDate();
     //   calendar.setTime(date);
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

        if (weekDataView.getChooseRoomDropdown() instanceof RoomComboBox) {
            RoomComboBox chooseRoomDropdown = (RoomComboBox) weekDataView.getChooseRoomDropdown();
            List<ReservationDTO> roomSchedule
                    // = scheduleFacade.getRoomSchedule(chooseRoomDropdown.getSelectedItem(), calendar.get(Calendar.YEAR), semester);
                    = scheduleFacade.getDetailedRoomSchedule(chooseRoomDropdown.getSelectedItem(), calendar.get(Calendar.YEAR), weekOfSemester, semester);

            DefaultTableModel defaultTableModel = new DefaultTableModelImpl(32, 8);

            for (ReservationDTO reservation : roomSchedule) {
                int endDay = reservation.getEndTime() / 96;
                int startDay = reservation.getStartTime() / 96;
                int numberOfEndQuarter = reservation.getEndTime() % 96 - 32; //rï¿½znica miï¿½dzy godzinami w bazie i tabeli
                int numberOfStartQuarter = reservation.getStartTime() % 96 - 32;

                if (numberOfEndQuarter > 31 || numberOfEndQuarter < 0) {
                    continue;
                }

                if (numberOfStartQuarter > 31 || numberOfStartQuarter < 0) {
                    continue;
                }

                for (int i = startDay + 1; i <= endDay + 1; i++) {
                    for (int j = numberOfStartQuarter; j <= numberOfEndQuarter; j++) {
                        defaultTableModel.setValueAt("T", j, i);
                    }
                }
                createReservationsRendererList(endDay, startDay,
                        numberOfStartQuarter, numberOfEndQuarter, reservation);
            }
            weekDataView.getPlanTable().setModel(defaultTableModel);

            weekDataView.getPlanTable().setDefaultRenderer(Object.class, new CustomRenderer(reservationCellsRendererMap));

        }
    }

    public void getRooms() {
        if (weekDataView.getChooseRoomDropdown() instanceof RoomComboBox) {
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
    }

    private void createReservationsRendererList(Integer endDay, Integer startDay,
            Integer numberOfStartQuarter, Integer numberOfEndQuarter, ReservationDTO reservation) {
        if (!Objects.equals(startDay, endDay)) {
            return;
        }
        //TODO get dane o kolorze z bazy
        Color color = null;
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        for (ReservationTypeDTO reservationType : reservationTypes) {
            if (reservationType.getShortDescription().equals(reservation.getType())) {
                try {
                    Field field = Color.class.getField(reservationType.getReservationColor());
                    color = (Color) field.get(null);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    color = null; // Not defined
                }
            }
        }
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
