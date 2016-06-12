package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditInstituteView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class AddEditInstituteMediator {

    private AddEditInstituteView addEditInstituteView;

    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();

    public AddEditInstituteMediator() {

    }

    public AddEditInstituteView createView(MainView window) {
        addEditInstituteView = new AddEditInstituteView(window, this);
        getInstitutesData();
        return addEditInstituteView;
    }

    private void getInstitutesData() {
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        addEditInstituteView.getInstituteCb().addItem("Create new");
        for (InstituteDTO institute : allInstitutes) {
            addEditInstituteView.getInstituteCb().addItem(institute.getName());
        }
        addEditInstituteView.getInstituteCb().setSelectedIndex(0);
    }

    public void onSelectionChange() {
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        String selectedItem = (String) addEditInstituteView.getInstituteCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            addEditInstituteView.getNameTf().setText("");
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (selectedItem.equals(institute.getName())) {
                addEditInstituteView.getNameTf().setText(institute.getName());
            }
        }
    }

    public void onAddEdit() {
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        String selectedItem = (String) addEditInstituteView.getInstituteCb().getSelectedItem();
        InstituteDTO instituteDTO = new InstituteDTO();
        if (selectedItem.equals("Create new")) {
            String name = addEditInstituteView.getNameTf().getText();
            instituteDTO.setName(name);
            roomManagementFacade.addInstitute(instituteDTO);
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (selectedItem.equals(institute.getName())) {
                if (addEditInstituteView.getNameTf().getText().equals(institute.getName())) {
                    return;
                }
                String name = addEditInstituteView.getNameTf().getText();
                instituteDTO.setName(name);
                instituteDTO.setId(institute.getId());
                roomManagementFacade.addInstitute(instituteDTO);
                return;
            }
        }
    }

    public void onDelete() {
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        String selectedItem = (String) addEditInstituteView.getInstituteCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (selectedItem.equals(institute.getName())) {
                roomManagementFacade.removeInstitute(institute);
            }
        }
    }
}
