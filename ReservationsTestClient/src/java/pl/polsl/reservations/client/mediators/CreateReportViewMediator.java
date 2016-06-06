package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.List;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.CreateRaportView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class CreateReportViewMediator {

    private CreateRaportView createRaportView;

    private final RoomManagementFacade roomManagementFacade;
    private final UserManagementFacade userManagementFacade;
    private final UserFacade userFacade;

    public CreateReportViewMediator() {
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userManagementFacade = (UserManagementFacade) Lookup.getRemote("UserManagementFacade");
        userFacade = (UserFacade) Lookup.getRemote("UserFacade");
    }

    public CreateRaportView createView(MainView view, int option) {
        createRaportView = new CreateRaportView(view, option, this);
        return createRaportView;
    }

    public void setAddRoomData() {
        List<DepartamentDTO> departamentsList = userManagementFacade.getAllDepartaments();
        for (DepartamentDTO departament : departamentsList) {
            createRaportView.getDepartmentCb().addItem(departament.getName());
        }
        List<UserDTO> usersList = userFacade.getUsersWithLowerPrivilegeLevel();
        for (UserDTO user : usersList) {
            if (user.getPrivilegeLevel() < 6) {
                String userData = user.getName() + " " + user.getSurname();
                createRaportView.getKeeperCb().addItem(userData);
            }
        }
    }

    public void onAddRoom() {
        String roomNumber = createRaportView.getRoomIdTf().getText();
        String numberOfSeats = createRaportView.getNumberTf().getText();
        String departament = (String) createRaportView.getDepartmentCb().getSelectedItem();
        String keeperData = (String) createRaportView.getKeeperCb().getSelectedItem();
        Integer keeperIndex = createRaportView.getKeeperCb().getSelectedIndex();
        List<UserDTO> usersList = userFacade.getUsersWithLowerPrivilegeLevel();
        List<UserDTO> keepersList = new ArrayList<>();
        for (UserDTO user : usersList) {
            if (user.getPrivilegeLevel() < 6) {
                keepersList.add(user);
            }
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setDepartment(departament);
        roomDTO.setNumber(Integer.parseInt(roomNumber));
        roomDTO.setType("TODO");
        roomDTO.setKeeper("dzik");
    }

}
