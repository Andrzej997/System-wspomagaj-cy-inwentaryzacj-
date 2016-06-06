package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author Paweï¿½
 */
public class DayDataViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private Calendar date;
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

        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);

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

        }
        List<ReservationDTO> roomSchedule = scheduleFacade.getDetailedRoomSchedule((Integer) dayDataView.getChooseRoomDropdown().getSelectedItem(), calendar.get(Calendar.YEAR), weekOfSemester, semester);
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
