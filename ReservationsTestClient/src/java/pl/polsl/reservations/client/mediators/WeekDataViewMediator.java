/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.mediators;

import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.schedule.ScheduleFacade;

/**
 *
 * @author Pawe³
 */
public class WeekDataViewMediator {

    private ScheduleFacade scheduleFacade;
    private RoomManagementFacade roomManagementFacade;
    private WeekDataView weekDataView;

    public WeekDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    }

    public WeekDataView createView(MainView parent, Object selectedItem) {
        weekDataView = new WeekDataView(parent, selectedItem, this);

        //DefaultTableModel defaultTableModel = new DefaultTableModel(32, 7);
       // for (int i = 0; i < 7; i++) {
       //     for (int j = 0; j < 32; j++) {
       //         defaultTableModel.setValueAt("", j, i);
       //     }
       // }
        //weekDataView.getPlanView().setModel(defaultTableModel);
        
        getReservations();
        
        return weekDataView;
    }

    public void getReservations() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        List<ReservationDTO> detailedRoomSchedule = scheduleFacade.getDetailedRoomSchedule(Integer.parseInt((String)weekDataView.getChooseRoomDropdown().getSelectedItem()) , date.getYear(), weekOfYear, true);

        DefaultTableModel defaultTableModel = new DefaultTableModel(32, 7);

        for (ReservationDTO reservation : detailedRoomSchedule) {
            int startDay = reservation.getStartTime() / 96;
            int endDay = reservation.getEndTime() / 96;
            int numberOfStartQuarter = reservation.getStartTime() % 96 - 32; //róznica miêdzy godzinami w bazie i tabeli
            int numberOfEndQuarter = reservation.getStartTime() % 96 - 32;

            for (int i = startDay; i < endDay; i++) {
                for (int j = numberOfStartQuarter; j < numberOfEndQuarter; j++) {
                    defaultTableModel.setValueAt("T", j, i);
                }
            }

        }

        weekDataView.getPlanView().setModel(defaultTableModel);
    }

}
