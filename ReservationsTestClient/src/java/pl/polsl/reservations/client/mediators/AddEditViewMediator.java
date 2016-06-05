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

    public AddEditViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
        date = Calendar.getInstance();

    }

    public AddEditView createView(MainView parent) {
        addEditView = new AddEditView(parent, this);
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
        });
    }

    public void getReservations() {
        reservationCellsRendererMap = new HashMap<>();
        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule((Integer) addEditView.getRoomCb().getSelectedItem(), 2016, true);

        DefaultTableModel defaultTableModel = new DayTableModel(32, 3);

        for (ReservationDTO reservation : roomSchedule) {
            int endDay = reservation.getEndTime() / 96;
            int startDay = reservation.getStartTime()/ 96;
            int numberOfEndQuarter = reservation.getEndTime() % 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96;

            int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1) {
                dayOfWeek = 7;
            } else {
                dayOfWeek--;
            }
            //TODO: dodano na sztywno poniedzialek zeby byly widoczne, wstawi� dayOfWeek
            if (startDay == (int) dayOfWeek) {

               /* for (int j = numberOfStartQuarter; j <= numberOfEndQuarter; j++) {
                    defaultTableModel.setValueAt("T", j, 0);
                }*/
                createReservationsRendererList(numberOfStartQuarter, numberOfEndQuarter, reservation);
            }
            addEditView.getDayTable().setModel(defaultTableModel);
            addEditView.getDayTable().setDefaultRenderer(Object.class,
                    new DayCustomRenderer(reservationCellsRendererMap));
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
    
    public Integer getStartHourFromView(){
        String selectedHour=(String)addEditView.getHourStartCb().getSelectedItem();
        for(int i = 0; i< 96; i++){
            Integer hour = i/4;
            Integer quarter = (i % 4)*15;
            String hourString = hour.toString() + ":";
            if(quarter == 0){
                hourString += "00";
            } else{
                hourString += quarter.toString();
            }
            if(selectedHour.equals(hourString)){
                return i;
            }
        }
        return null;
    }
    
    public void setWorkersData(){
        UserDTO currentUserDetails = userManagementFacade.getUserDetails(ClientContext.getUsername());
        List<UserDTO> userDetailsList = userFacade.getUsersWithLowerPrivilegeLevel();
        userDetailsList.stream().forEach((userDTO) -> {
            addEditView.getTeacherCb().addItem(userDTO.getName() + " " + userDTO.getSurname());
        });
        String name = currentUserDetails.getName() + " " + currentUserDetails.getSurname();
        addEditView.getTeacherCb().addItem(name);
        addEditView.getTeacherCb().setSelectedItem(name);
    }
    
    public void setTargetData(){
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        for(ReservationTypeDTO reservationTypeDTO : reservationTypes){
            addEditView.getGroupCb().addItem(reservationTypeDTO.getShortDescription());
        }
    }

}
