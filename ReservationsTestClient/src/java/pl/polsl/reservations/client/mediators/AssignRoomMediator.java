package pl.polsl.reservations.client.mediators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.client.views.AssignRoomView;
import pl.polsl.reservations.client.views.MainView;
import pl.polsl.reservations.client.views.utils.MessageBoxUtils;
import pl.polsl.reservations.dto.RoomDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class AssignRoomMediator {

    private AssignRoomView assignRoomView;

    private final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private final RoomManagementFacade roomManagementFacade = Lookup.getRoomManagementFacade();
    private final UserFacade userFacade = Lookup.getUserFacade();

    public AssignRoomMediator() {
    }

    public AssignRoomView createView(MainView window) {
        assignRoomView = new AssignRoomView(window, this);
        getUnderlings();
        getRooms();
        getRoomDetails();
        return assignRoomView;
    }

    private void getUnderlings() {
        Long currentUserId = ClientContext.getInstance().getCurrentUserId();
        List<UserDTO> underlings = null;
        if (ClientContext.getInstance().isAdmin()) {
            underlings = userFacade.getUsersWithLowerPrivilegeLevel();
        } else {
            underlings = userManagementFacade.getUnderlings(currentUserId.intValue());
        }
        for (UserDTO worker : underlings) {
            assignRoomView.getWorkerCb().addItem(worker.getId() + " " + worker.getName() + " " + worker.getSurname());
        }
        if (underlings != null) {
            assignRoomView.getWorkerCb().setSelectedIndex(0);
        }
    }

    private void getRooms() {
        Long currentUserId = ClientContext.getInstance().getCurrentUserId();
        List<RoomDTO> roomsList = null;
        if (ClientContext.getInstance().isDepartamentChief()) {
            roomsList = roomManagementFacade.getDepartamentRooms(currentUserId);
        } else if (ClientContext.getInstance().isInstituteChief()) {
            roomsList = roomManagementFacade.getInstituteRooms(currentUserId);
        } else if (ClientContext.getInstance().isAdmin()) {
            roomsList = roomManagementFacade.getRoomsList();
        }
        if(roomsList == null || roomsList.isEmpty()){
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
        assignRoomView.getRoomCb().setFloors(floors);
        for (Map.Entry<Integer, List<Integer>> floorEntry : numbersMap.entrySet()) {
            assignRoomView.getRoomCb().setRooms(floorEntry.getValue(), floorEntry.getKey());
        }
        UserDTO selectedWorker = getSelectedWorker();
        if (selectedWorker != null) {
            Integer roomNumber = selectedWorker.getRoomNumber();
            if (roomNumber != null) {
                Integer floor = roomNumber / 100;
                Integer number = roomNumber % 100;
                assignRoomView.getRoomCb().selectItem(floor, number);
                return;
            }
        }
        Set<Integer> keySet = numbersMap.keySet();
        Object[] keyArray = keySet.toArray();
        assignRoomView.getRoomCb().selectItem((Integer) keyArray[0],
                numbersMap.get((Integer) keyArray[0]).get(0));

    }

    private UserDTO getSelectedWorker() {
        String selectedItem = (String) assignRoomView.getWorkerCb().getSelectedItem();
        if(selectedItem == null)
            return null;
        int index = selectedItem.indexOf(" ");
        String userIdStr = selectedItem.substring(0, index);
        Integer userId = Integer.parseInt(userIdStr);
        UserDTO userDetails = userManagementFacade.getUserDetails(userId);
        return userDetails;
    }

    private void getRoomDetails() {
        Integer roomNumber = assignRoomView.getRoomCb().getSelectedItem();
        if (roomNumber != null) {
            RoomDTO room = roomManagementFacade.getRoom(roomNumber);
            List<UserDTO> roomWorkers = roomManagementFacade.getRoomWorkers(room.getId());
            UserDTO roomKeeper = roomManagementFacade.getRoomKeeper(room.getId().intValue());
            if (room != null) {
                assignRoomView.getRoomTypeContentLb().setText(room.getType());
                assignRoomView.getRoomSeatsContentLb().setText(room.getNumberOfSeats().toString());
            } else {
                assignRoomView.getRoomTypeContentLb().setText("");
                assignRoomView.getRoomSeatsContentLb().setText("");
            }
            if (roomKeeper != null) {
                assignRoomView.getCurrentKeeperContentLb().setText(roomKeeper.getName() + " " + roomKeeper.getSurname());
            } else {
                assignRoomView.getCurrentKeeperContentLb().setText("");
            }
            if (roomWorkers != null && !roomWorkers.isEmpty()) {
                DefaultListModel model = new DefaultListModel();
                for (UserDTO worker : roomWorkers) {
                    model.addElement(worker.getId() + " " + worker.getName() + " " + worker.getSurname());
                }
                assignRoomView.getCurrentWorkersLs().setModel(model);
            }
        }
    }

    private void selectRoom(UserDTO selectedWorker) {
        if (selectedWorker != null) {
            Integer roomNumber = selectedWorker.getRoomNumber();
            if (roomNumber != null) {
                Integer floor = roomNumber / 100;
                Integer number = roomNumber % 100;
                assignRoomView.getRoomCb().selectItem(floor, number);
            }
        }
    }

    public void onUserSelectionChange() {
        UserDTO selectedWorker = getSelectedWorker();
        selectRoom(selectedWorker);
        getRoomDetails();
    }

    public void onRoomSelectionChange() {
        getRoomDetails();
    }

    public void onAssign() {
        UserDTO selectedWorker = getSelectedWorker();
        Integer roomNumber = assignRoomView.getRoomCb().getSelectedItem();
        boolean asKeeper = assignRoomView.getAsKeeperChb().isSelected();
        if(selectedWorker == null){
            return;
        }
        if(selectedWorker.getRoomNumber() != null){
            if(!MessageBoxUtils.userAlreadyAssignedWarrning(assignRoomView, roomNumber)){
                return;
            }
        }
        if(asKeeper){
            RoomDTO room = roomManagementFacade.getRoom(roomNumber);
            if(room == null || selectedWorker.getId().equals(room.getKeeperId())){
                return;
            }
            roomManagementFacade.assignKeeperToRoom(room.getId().intValue(), selectedWorker.getId().intValue());
        } else {
            RoomDTO room = roomManagementFacade.getRoom(roomNumber);
            List<UserDTO> roomWorkers = roomManagementFacade.getRoomWorkers(room.getId());
            if(room == null || roomWorkers == null || roomWorkers.isEmpty() || roomWorkers.contains(selectedWorker)){
                return;
            }
            roomManagementFacade.assignUserToRoom(room.getId().intValue(), selectedWorker.getId().intValue());
        }
    }
}
