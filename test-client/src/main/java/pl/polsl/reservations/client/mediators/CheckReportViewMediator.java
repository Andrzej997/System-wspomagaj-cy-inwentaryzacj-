package pl.polsl.reservations.client.mediators;

import pl.polsl.reservations.client.views.CheckRaportView;
import pl.polsl.reservations.client.views.MainView;

/**
 *
 * @author matis
 */
public class CheckReportViewMediator {

    private CheckRaportView checkRaportView;

    public CheckReportViewMediator() {

    }

    public CheckRaportView createView(MainView view) {
        checkRaportView = new CheckRaportView(view, this);

        return checkRaportView;
    }

}
