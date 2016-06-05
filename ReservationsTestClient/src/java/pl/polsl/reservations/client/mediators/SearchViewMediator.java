package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.SearchView;

/**
 *
 * @author matis
 */
public class SearchViewMediator {
    
    private SearchView searchView;
    
    public SearchViewMediator(){
    }
    
    public SearchView createView(MainView view){
        searchView = new SearchView(view, this);
        return searchView;
    }
    
}
