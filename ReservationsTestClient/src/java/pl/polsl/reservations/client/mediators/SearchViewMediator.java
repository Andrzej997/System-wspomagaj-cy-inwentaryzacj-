package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.SearchView;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;

/**
 *
 * @author matis
 */
public class SearchViewMediator {
    
    private SearchView searchView;
    private final ScheduleFacade scheduleFacade;
    
    public SearchViewMediator(){
        scheduleFacade = (ScheduleFacade) Lookup.getRemote("ScheduleFacade");
    }
    
    public SearchView createView(MainView view){
        searchView = new SearchView(view, this);
        setReservationTypes();
        return searchView;
    }
    
    public void setReservationTypes(){
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        for(ReservationTypeDTO reservationTypeDTO : reservationTypes){
            searchView.getTypeCb().addItem(reservationTypeDTO.getShortDescription());
        }
    }
    
    
    
}
