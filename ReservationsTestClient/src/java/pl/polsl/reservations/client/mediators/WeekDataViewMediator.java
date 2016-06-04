package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

/**
 *
 * @author Pawe�
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
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        if (weekDataView.getChooseRoomDropdown() instanceof RoomComboBox) {
            RoomComboBox chooseRoomDropdown = (RoomComboBox) weekDataView.getChooseRoomDropdown();
            List<ReservationDTO> roomSchedule
                    = scheduleFacade.getRoomSchedule(chooseRoomDropdown.getSelectedItem(), 2016, true);

            DefaultTableModel defaultTableModel = new DefaultTableModel(32, 7);

            for (ReservationDTO reservation : roomSchedule) {
                int endDay = reservation.getStartTime() / 96;
                int startDay = reservation.getEndTime() / 96;
                int numberOfEndQuarter = reservation.getStartTime() % 96 - 32; //r�znica mi�dzy godzinami w bazie i tabeli
                int numberOfStartQuarter = reservation.getEndTime() % 96 - 32;

                /*   if (numberOfEndQuarter > 31) {
                    numberOfEndQuarter = 31;
                }

                if (numberOfEndQuarter < 0) {
                    numberOfEndQuarter = 0;
                }

                if (numberOfStartQuarter > 31) {
                    numberOfStartQuarter = 31;
                }

                if (numberOfStartQuarter < 0) {
                    numberOfStartQuarter = 0;
                }*/
                if (numberOfEndQuarter > 31 || numberOfEndQuarter < 0) {
                    continue;
                }

                if (numberOfStartQuarter > 31 || numberOfStartQuarter < 0) {
                    continue;
                }

                for (int i = startDay; i <= endDay; i++) {
                    for (int j = numberOfStartQuarter; j <= numberOfEndQuarter; j++) {
                        defaultTableModel.setValueAt("T", j, i);
                        createReservationsRendererList(endDay, startDay,
                                numberOfStartQuarter, numberOfEndQuarter, reservation);

                    }
                }
            }
            weekDataView.getPlanTable().setModel(defaultTableModel);

            weekDataView.getPlanTable().setDefaultRenderer(Object.class, new CustomRenderer(reservationCellsRendererMap));

            //  DefaultTableModel model = (DefaultTableModel) weekDataView.getPlanView().getModel();
            //TableColumnModel columnModel = weekDataView.getPlanView().getColumnModel();
            //for (int i = 0; i < columnModel.getColumnCount(); i++) {
            //   columnModel.getColumn(i).setCellRenderer(new CustomRenderer());
            // }
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

    //TODO
    private void createReservationsRendererList(Integer endDay, Integer startDay,
            Integer numberOfStartQuarter, Integer numberOfEndQuarter, ReservationDTO reservation) {
        if (!Objects.equals(startDay, endDay)) {
            return;
        }
        //TODO get dane o kolorze z bazy
        String type = reservation.getType();
        // TODO z reservation.getID();
        // scheduleFacade.getColorByID(id);

        Color color = Color.BLUE;
        /* try {
            Field field = Color.class.getField("colorName");
            color = (Color) field.get(null);
        } catch (Exception e) {
            color = null; // Not defined
        }*/
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
}
