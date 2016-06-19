package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AssignToUnitView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.MessageBoxUtils;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class AssignToUnitMediator {

    private AssignToUnitView assignToUnitView;

    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();
    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private InstituteDTO selectedInstitute = null;
    private final List<DepartamentDTO> departaments;

    public AssignToUnitMediator() {
        departaments = new ArrayList<>();
    }

    public AssignToUnitView createView(MainView window) {
        assignToUnitView = new AssignToUnitView(window, this);
        getInstituteData();
        getDepartaments();
        getRooms();
        getRoomData();
        return assignToUnitView;
    }

    private void getInstituteData() {
        Long currentUserId = ClientContext.getInstance().getCurrentUserId();
        List<InstituteDTO> allInstitutes = userManagementFacade.getAllInstitutes();
        if (allInstitutes == null || allInstitutes.isEmpty()) {
            return;
        }
        for (InstituteDTO institute : allInstitutes) {
            if (institute.getChefId().equals(currentUserId)) {
                selectedInstitute = institute;
            }
        }
        if (ClientContext.getInstance().isAdmin()) {
            selectedInstitute = allInstitutes.get(0);
        }
        if (selectedInstitute == null) {
            return;
        }
        assignToUnitView.getInstituteContentLb().setText(selectedInstitute.getName());
    }

    private void getDepartaments() {
        if (selectedInstitute == null) {
            return;
        }
        List<DepartamentDTO> allDepartaments = userManagementFacade.getAllDepartaments();
        if (allDepartaments == null || allDepartaments.isEmpty()) {
            return;
        }
        for (DepartamentDTO departament : allDepartaments) {
            if (departament.getInstitute().getId().equals(selectedInstitute.getId())) {
                assignToUnitView.getDepartamentCb().addItem(departament.getName());
                departaments.add(departament);
            }
        }
        if (assignToUnitView.getDepartamentCb().getModel().getSize() > 0) {
            assignToUnitView.getDepartamentCb().setSelectedIndex(0);
        }
    }

    private void getRooms() {
        List<RoomDTO> roomsList = roomManagementFacade.getRoomsList();
        if (roomsList == null || roomsList.isEmpty()) {
            return;
        }
        HashMap<Integer, List<Integer>> numbersMap = new HashMap<>();
        List<Integer> floors = new ArrayList<>();
        for (RoomDTO room : roomsList) {
            Integer roomNumber = room.getNumber();
            Integer floor = roomNumber / 100;
            Integer number = roomNumber % 100;
            if (numbersMap.containsKey(floor)) {
                List<Integer> numbers = numbersMap.get(floor);
                numbers.add(number);
                numbersMap.put(floor, numbers);
            } else {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(number);
                numbersMap.put(floor, numbers);
                floors.add(floor);
            }
        }
        assignToUnitView.getRoomCb().setFloors(floors);
        for (Map.Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            assignToUnitView.getRoomCb().setRooms(floorEntry.getValue(), floorEntry.getKey());
        }

        Set<Integer> keySet = numbersMap.keySet();
        Object[] keyArray = keySet.toArray();
        assignToUnitView.getRoomCb().selectItem((Integer) keyArray[0],
                numbersMap.get((Integer) keyArray[0]).get(0));
    }

    private void getRoomData() {
        Integer selectedRoomNumber = assignToUnitView.getRoomCb().getSelectedItem();
        if (selectedRoomNumber == null) {
            return;
        }
        RoomDTO room = roomManagementFacade.getRoom(selectedRoomNumber);
        if (room == null) {
            return;
        }
        assignToUnitView.getRoomTypeContentLb().setText(room.getType());
        int selectedIndex = assignToUnitView.getDepartamentCb().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        DepartamentDTO departament = departaments.get(selectedIndex);
        if (departament == null) {
            return;
        }
        List<RoomDTO> departamentRooms = roomManagementFacade.getDepartamentRoomsById(departament.getId());
        if (departamentRooms == null || departamentRooms.isEmpty()) {
            return;
        }
        DefaultListModel model = new DefaultListModel();
        for (RoomDTO r : departamentRooms) {
            model.addElement(r.getNumber().toString());
        }
        assignToUnitView.getDepRoomsLst().setModel(model);
    }

    public void onDepartamentChange() {
        getRoomData();
    }

    public void onRoomChange() {
        getRoomData();
    }

    public void onAssign() {
        int selectedIndex = assignToUnitView.getDepartamentCb().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        DepartamentDTO departament = departaments.get(selectedIndex);
        if (departament == null) {
            return;
        }
        Integer selectedRoomNumber = assignToUnitView.getRoomCb().getSelectedItem();
        if (selectedRoomNumber == null) {
            return;
        }
        RoomDTO room = roomManagementFacade.getRoom(selectedRoomNumber);
        if (room == null) {
            return;
        }
        if (!room.getDepartment().equals(departament.getName())) {
            if (!MessageBoxUtils.roomAlreadyAssignedWarrning(assignToUnitView, room.getDepartment())) {
                return;
            }
        }
        if (room.getDepartment().equals(departament.getName())) {
            return;
        }
        roomManagementFacade.assignRoomToDepartament(room.getId(), departament.getId());

    }

}
