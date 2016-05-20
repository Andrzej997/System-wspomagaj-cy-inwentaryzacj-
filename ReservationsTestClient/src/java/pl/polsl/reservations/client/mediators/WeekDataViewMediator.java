/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.mediators;

import com.google.common.collect.Table;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.roomManagement.RoomManagementFacade;
import pl.polsl.reservations.schedule.ScheduleFacade;
import pl.polsl.reservations.client.views.renderers.CustomRenderer;

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

        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule(Integer.parseInt((String)weekDataView.getChooseRoomDropdown().getSelectedItem()), 2016, true);

        DefaultTableModel defaultTableModel = new DefaultTableModel(32, 7);

        roomSchedule.stream().forEach((reservation) -> {
            int endDay = reservation.getStartTime() / 96;
            int startDay = reservation.getEndTime() / 96;
            int numberOfEndQuarter = reservation.getStartTime() % 96 - 32; //róznica miêdzy godzinami w bazie i tabeli
            int numberOfStartQuarter = reservation.getEndTime() % 96 - 32;
            
            if(numberOfEndQuarter>31){
                numberOfEndQuarter=31;
            }
            
            if(numberOfEndQuarter<0){
                numberOfEndQuarter=0;
            }
            
            if(numberOfStartQuarter>31){
                numberOfStartQuarter=31;
            }
            
            if(numberOfStartQuarter<0){
                numberOfStartQuarter=0;
            }
            

            for (int i = startDay; i <= endDay; i++) {
                for (int j = numberOfStartQuarter; j <= numberOfEndQuarter; j++) {
                    defaultTableModel.setValueAt("T", j, i);
                    
                }
            }
        });
        weekDataView.getPlanView().setModel(defaultTableModel);
        weekDataView.getPlanView().setDefaultRenderer(Object.class, new CustomRenderer());
    /*    
        DefaultTableModel model = (DefaultTableModel)weekDataView.getPlanView().getModel();
        TableColumnModel columnModel = weekDataView.getPlanView().getColumnModel();
        for(int i = 0; i< columnModel.getColumnCount(); i++){
            columnModel.getColumn(i).setCellRenderer(new CustomRenderer());
        }*/
    }

}
