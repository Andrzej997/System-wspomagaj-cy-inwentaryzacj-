package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditReservationTypeView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.ColorUtils;
import pl.polsl.reservations.dto.ReservationTypeDTO;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;

/**
 *
 * @author matis
 */
public class AddEditReservationTypeMediator {

    private AddEditReservationTypeView addEditReservationTypeView;

    private final ScheduleFacade scheduleFacade = Lookup.getScheduleFacade();

    public AddEditReservationTypeMediator() {

    }

    public AddEditReservationTypeView createView(MainView window) {
        addEditReservationTypeView = new AddEditReservationTypeView(window, this);
        getReservationTypes();
        return addEditReservationTypeView;
    }

    private void getReservationTypes() {
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        addEditReservationTypeView.getTypeCb().addItem("Create new");
        for (ReservationTypeDTO reservationType : reservationTypes) {
            addEditReservationTypeView.getTypeCb().addItem(reservationType.getShortDescription());
        }
        if (reservationTypes != null) {
            addEditReservationTypeView.getTypeCb().setSelectedIndex(0);
        }
        String[] colorList = ColorUtils.getColorList();
        for (String colorName : colorList) {
            addEditReservationTypeView.getColorCb().addItem(colorName);
        }
    }

    public void onSelectionChange() {
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        String selectedItem = (String) addEditReservationTypeView.getTypeCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            addEditReservationTypeView.getShortDescTf().setText("");
            addEditReservationTypeView.getLongDescTf().setText("");
            ButtonStyle.setStyle(addEditReservationTypeView.getAddButton(), addEditReservationTypeView.getAddImg());
        } else {
            for (ReservationTypeDTO reservationType : reservationTypes) {
                if (reservationType.getShortDescription().equals(selectedItem)) {
                    addEditReservationTypeView.getShortDescTf().setText(reservationType.getShortDescription());
                    addEditReservationTypeView.getLongDescTf().setText(reservationType.getLongDescription());
                    ButtonStyle.setStyle(addEditReservationTypeView.getAddButton(), addEditReservationTypeView.getEditImg());
                    return;
                }
            }
        }
    }

    public void onAddEdit() {
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        String selectedItem = (String) addEditReservationTypeView.getTypeCb().getSelectedItem();
        ReservationTypeDTO reservationTypeDTO = new ReservationTypeDTO();
        if (selectedItem.equals("Create new")) {
            reservationTypeDTO.setShortDescription(addEditReservationTypeView.getShortDescTf().getText());
            reservationTypeDTO.setLongDescription(addEditReservationTypeView.getLongDescTf().getText());
            String selectedColor = (String) addEditReservationTypeView.getColorCb().getSelectedItem();
            reservationTypeDTO.setReservationColor(selectedColor);
            scheduleFacade.createReservationType(reservationTypeDTO);
        } else {
            for (ReservationTypeDTO reservationType : reservationTypes) {
                if (reservationType.getShortDescription().equals(selectedItem)) {
                    if (reservationType.getShortDescription().equals(addEditReservationTypeView.getShortDescTf().getText())
                            && reservationType.getLongDescription().equals(addEditReservationTypeView.getLongDescTf().getText())
                            && reservationType.getReservationColor().equals((String) addEditReservationTypeView.getColorCb().getSelectedItem())) {
                        return;
                    } else {
                        reservationTypeDTO.setShortDescription(addEditReservationTypeView.getShortDescTf().getText());
                        reservationTypeDTO.setLongDescription(addEditReservationTypeView.getLongDescTf().getText());
                        String selectedColor = (String) addEditReservationTypeView.getColorCb().getSelectedItem();
                        reservationTypeDTO.setReservationColor(selectedColor);
                        reservationTypeDTO.setId(reservationType.getId());
                        scheduleFacade.editReservationType(reservationType);
                        return;
                    }
                }
            }
        }
    }

    public void onDelete() {
        List<ReservationTypeDTO> reservationTypes = scheduleFacade.getReservationTypes();
        String selectedItem = (String) addEditReservationTypeView.getTypeCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            return;
        }
        for (ReservationTypeDTO reservationType : reservationTypes) {
            if (reservationType.getShortDescription().equals(selectedItem)) {
                scheduleFacade.removeReservationType(reservationType.getId().intValue());
                return;
            }
        }
    }
}
