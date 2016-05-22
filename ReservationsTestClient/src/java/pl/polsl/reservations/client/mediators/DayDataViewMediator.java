/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.mediators;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.DayDataView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.schedule.ScheduleFacade;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;

/**
 *
 * @author Pawe�
 */
public class DayDataViewMediator {

    private ScheduleFacade scheduleFacade;
    private RoomManagementFacade roomManagementFacade;
    private Object date;
    private DayDataView dayDataView;

    public DayDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    }

    public DayDataView createView(MainView parent, Object date) {
        dayDataView = new DayDataView(parent, date, this);

        this.date =  date;

        getRooms();
        getReservations();

        return dayDataView;
    }

    public void getReservations() {
       // Calendar calendar = Calendar.getInstance();
       // calendar.setTime(date);
       // int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
       // int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule( (Integer)dayDataView.getChooseRoomDropdown().getSelectedItem(), 2016, true);

        DefaultTableModel defaultTableModel = new DefaultTableModel(96, 1);

        for (ReservationDTO reservation : roomSchedule) {
            int endDay = reservation.getStartTime() / 96;
            int startDay = reservation.getEndTime() / 96;
            int numberOfEndQuarter = reservation.getStartTime() % 96; //r�znica mi�dzy godzinami w bazie i tabeli
            int numberOfStartQuarter = reservation.getEndTime() % 96;

            if (startDay == (int)date-1) {

                for (int j = numberOfStartQuarter; j <= numberOfEndQuarter; j++) {
                    defaultTableModel.setValueAt("T", j, 0);
                }
            }

        }

        dayDataView.getPlanView().setModel(defaultTableModel);
    }
    
    public void getRooms(){
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        roomsList.stream().forEach((room) -> {
            dayDataView.getChooseRoomDropdown().addItem(room.getNumber());
        });    
    }

}
