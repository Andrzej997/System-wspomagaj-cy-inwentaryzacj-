package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.DayDataView;
import pl.polsl.reservations.client.views.DayTableModel;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.renderers.DayCustomRenderer;
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

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private Object date;
    private DayDataView dayDataView;
    private HashMap<Color, List<Integer>> reservationCellsRendererMap;
    private List<Integer> startQuarters;
    private List<Integer> endQuarters;

    public DayDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        reservationCellsRendererMap = new HashMap<>();
    }

    public DayDataView createView(MainView parent, Object date) {
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
        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule((Integer) dayDataView.getChooseRoomDropdown().getSelectedItem(), 2016, true);

        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);

        for (ReservationDTO reservation : roomSchedule) {
            int endDay = reservation.getEndTime() / 96;
            int startDay = reservation.getStartTime() / 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96;
            if (startDay == (int) date - 1) {
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
            dayDataView.getPlanView().setModel(defaultTableModel);
            dayDataView.getPlanView().setDefaultRenderer(Object.class,
                    new DayCustomRenderer(reservationCellsRendererMap, startQuarters, endQuarters));
        }

        dayDataView.getPlanView().setModel(defaultTableModel);
    }

    public void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        roomsList.stream().forEach((room) -> {
            dayDataView.getChooseRoomDropdown().addItem(room.getNumber());
        });
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

}
