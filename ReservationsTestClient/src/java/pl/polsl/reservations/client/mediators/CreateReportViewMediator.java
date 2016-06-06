package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.CreateRaportView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.AddTypeEnum;

/**
 *
 * @author matis
 */
public class CreateReportViewMediator {
    
    private CreateRaportView createRaportView;
    
    public CreateReportViewMediator(){
        
    }
    
    public CreateRaportView createView(MainView view, AddTypeEnum option){
        createRaportView = new CreateRaportView(view, option, this);
        return createRaportView;
    }
    
}
