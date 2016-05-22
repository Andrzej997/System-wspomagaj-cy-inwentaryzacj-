package pl.polsl.reservations.client.mediators;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.WeekDataView;
import pl.polsl.reservations.dto.ReservationDTO;
import pl.polsl.reservations.client.views.renderers.CustomRenderer;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;

/**
 *
 * @author Pawe�
 */
public class WeekDataViewMediator {

    private final ScheduleFacade scheduleFacade;
    private final RoomManagementFacade roomManagementFacade;
    private WeekDataView weekDataView;

    public WeekDataViewMediator() {
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
    }

    public WeekDataView createView(MainView parent, Object selectedItem) {
        weekDataView = new WeekDataView(parent, selectedItem, this);
        getRooms();
        weekDataView.getChooseRoomDropdown()
                .setSelectedItem(weekDataView.getSelectedItem());
        getReservations();
        
        return weekDataView;
    }

    public void getReservations() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        List<ReservationDTO> roomSchedule = scheduleFacade.getRoomSchedule((Integer)weekDataView.getChooseRoomDropdown().getSelectedItem(), 2016, true);

        DefaultTableModel defaultTableModel = new DefaultTableModel(32, 7);

        roomSchedule.stream().forEach((reservation) -> {
            int endDay = reservation.getStartTime() / 96;
            int startDay = reservation.getEndTime() / 96;
            int numberOfEndQuarter = reservation.getStartTime() % 96 - 32; //r�znica mi�dzy godzinami w bazie i tabeli
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
    public void getRooms(){
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        roomsList.stream().forEach((room) -> {
            weekDataView.getChooseRoomDropdown().addItem(room.getNumber());
        });    
    }
}
