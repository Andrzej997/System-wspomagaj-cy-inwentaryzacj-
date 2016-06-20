package pl.polsl.reservations.client.mediators;

import java.util.List;
import java.util.Objects;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditInstituteView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.client.views.utils.MessageBoxUtils;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.UserDTO;
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
        List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(3);
        Long chefId = allInstitutes.get(0).getChefId();
        UserDTO userDetails = userManagementFacade.getUserDetails(chefId.intValue());
        Integer selectedIndex = 0;
        Integer counter = 0;
        for (UserDTO user : possibleChiefs) {
            addEditInstituteView.getChiefCb().addItem(user.getName() + " " + user.getSurname());
            if (Objects.equals(user.getId(), userDetails.getId())) {
                selectedIndex = counter;
            }
            counter++;
        }
        addEditInstituteView.getChiefCb().setSelectedIndex(selectedIndex);
    }

    public void onSelectionChange() {
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        String selectedItem = (String) addEditInstituteView.getInstituteCb().getSelectedItem();
        List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(3);
        if (selectedItem.equals("Create new")) {
            addEditInstituteView.getNameTf().setText("");
            ButtonStyle.setStyle(addEditInstituteView.getAddButton(), addEditInstituteView.getAddImg());
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (selectedItem.equals(institute.getName())) {
                addEditInstituteView.getNameTf().setText(institute.getName());
                Long chefId = institute.getChefId();
                UserDTO userDetails = userManagementFacade.getUserDetails(chefId.intValue());
                Integer selectedIndex = 0;
                Integer counter = 0;
                for (UserDTO user : possibleChiefs) {
                    if (Objects.equals(user.getId(), userDetails.getId())) {
                        selectedIndex = counter;
                    }
                    counter++;
                }
                addEditInstituteView.getChiefCb().setSelectedIndex(selectedIndex);
                ButtonStyle.setStyle(addEditInstituteView.getAddButton(), addEditInstituteView.getEditImg());
                return;
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
            instituteDTO.setChefId(getSelectedChief());
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
                instituteDTO.setChefId(getSelectedChief());
                instituteDTO.setId(institute.getId());
                roomManagementFacade.editInstitute(instituteDTO);
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
        if(!MessageBoxUtils.deleteConfirmationWarning(addEditInstituteView, "selected institute")){
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (selectedItem.equals(institute.getName())) {
                roomManagementFacade.removeInstitute(institute.getId());
                return;
            }
        }
    }

    private Long getSelectedChief() {
        List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(3);
        int selectedIndex = addEditInstituteView.getChiefCb().getSelectedIndex();
        return possibleChiefs.get(selectedIndex).getId();
    }
}
