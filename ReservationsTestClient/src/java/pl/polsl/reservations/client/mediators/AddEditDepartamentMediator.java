package pl.polsl.reservations.client.mediators;

import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AddEditDepartamentView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.ButtonStyle;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class AddEditDepartamentMediator {

    private AddEditDepartamentView addEditDepartamentView;
    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();

    public AddEditDepartamentMediator() {

    }

    public AddEditDepartamentView createView(MainView window) {
        addEditDepartamentView = new AddEditDepartamentView(window, this);
        getDepartamentsData();
        return addEditDepartamentView;
    }

    private void getDepartamentsData() {
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        addEditDepartamentView.getDepartamentCb().addItem("Create new");
        for (DepartamentDTO departamentDTO : allDepartaments) {
            addEditDepartamentView.getDepartamentCb().addItem(departamentDTO.getName());
        }
        addEditDepartamentView.getDepartamentCb().setSelectedIndex(0);
        for (InstituteDTO institute : allInstitutes) {
            addEditDepartamentView.getInstituteCb().addItem(institute.getName());
        }
        addEditDepartamentView.getInstituteCb().setSelectedIndex(0);
        List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(4);
        Long departamentChief = roomManagementFacade.getDepartamentChief(allDepartaments.get(0).getId());
        UserDTO chief = possibleChiefs.get(departamentChief.intValue());
        Integer counter = 0;
        Integer selectedIndex = 0;
        for (UserDTO user : possibleChiefs) {
            addEditDepartamentView.getChiefCb().addItem(user.getName() + " " + user.getSurname());
            if (user.getId().equals(chief.getId())) {
                selectedIndex = counter;
            }
            counter++;
        }
        addEditDepartamentView.getChiefCb().setSelectedIndex(selectedIndex);
    }

    public void onSelectionChange() {
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        String selectedItem = (String) addEditDepartamentView.getDepartamentCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            addEditDepartamentView.getNameTf().setText("");
            if (addEditDepartamentView.getInstituteCb().getSelectedItem() != null) {
                addEditDepartamentView.getInstituteCb().setSelectedIndex(0);
            }
            ButtonStyle.setStyle(addEditDepartamentView.getAddButton(), addEditDepartamentView.getAddImg());
            return;
        }
        for (DepartamentDTO departamentDTO : allDepartaments) {
            if (departamentDTO.getName().equals(selectedItem)) {
                addEditDepartamentView.getNameTf().setText(departamentDTO.getName());
                addEditDepartamentView.getDepartamentCb().setSelectedItem(departamentDTO.getInstitute().getName());
                List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(4);
                Long departamentChief = roomManagementFacade.getDepartamentChief(departamentDTO.getId());
                UserDTO chief = possibleChiefs.get(departamentChief.intValue());
                Integer counter = 0;
                Integer selectedIndex = 0;
                for (UserDTO user : possibleChiefs) {
                    if (user.getId().equals(chief.getId())) {
                        selectedIndex = counter;
                    }
                    counter++;
                }
                addEditDepartamentView.getChiefCb().setSelectedIndex(selectedIndex);
                ButtonStyle.setStyle(addEditDepartamentView.getAddButton(), addEditDepartamentView.getEditImg());
                return;
            }
        }
    }

    public void onAddEdit() {
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        String selectedItem = (String) addEditDepartamentView.getDepartamentCb().getSelectedItem();
        DepartamentDTO departamentDTO = new DepartamentDTO();
        if (selectedItem.equals("Create new")) {
            departamentDTO.setName(addEditDepartamentView.getNameTf().getText());
            int selectedIndex = addEditDepartamentView.getInstituteCb().getSelectedIndex();
            InstituteDTO institute = allInstitutes.get(selectedIndex);
            departamentDTO.setInstitute(institute);
            Long chiefId = getSelectedChief();
            roomManagementFacade.addDepartament(departamentDTO, chiefId, institute.getId());
            return;
        }
        for (DepartamentDTO departament : allDepartaments) {
            if (selectedItem.equals(departament.getName())) {
                int selectedIndex = addEditDepartamentView.getInstituteCb().getSelectedIndex();
                InstituteDTO institute = allInstitutes.get(selectedIndex);
                if (departament.getName().equals(addEditDepartamentView.getNameTf().getText())
                        && departament.getInstitute().getName().equals(institute.getName())) {
                    return;
                }
                departamentDTO.setName(addEditDepartamentView.getNameTf().getText());
                departamentDTO.setInstitute(institute);
                departamentDTO.setId(departament.getId());
                Long chiefId = getSelectedChief();
                roomManagementFacade.editDepartament(departamentDTO, chiefId, institute.getId());
                return;
            }
        }
    }

    public void onDelete() {
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        String selectedItem = (String) addEditDepartamentView.getDepartamentCb().getSelectedItem();
        if (selectedItem.equals("Create new")) {
            return;
        }
        for (DepartamentDTO departament : allDepartaments) {
            if (selectedItem.equals(departament.getName())) {
                roomManagementFacade.removeDepartament(departament);
                return;
            }
        }
    }

    private Long getSelectedChief() {
        List<UserDTO> possibleChiefs = userManagementFacade.getPossibleChiefs(4);
        int selectedIndex = addEditDepartamentView.getChiefCb().getSelectedIndex();
        UserDTO chief = possibleChiefs.get(selectedIndex);
        return chief.getId();
    }

}
