package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.TutorialView;

/**
 *
 * @author matis
 */
public class TutorialViewMediator {
    
    private TutorialView tutorialView;
    
    public TutorialViewMediator(){
        
    }
    
    public TutorialView createView(MainView view){
        tutorialView = new TutorialView(view, this);
        return tutorialView;
    }
    
}
