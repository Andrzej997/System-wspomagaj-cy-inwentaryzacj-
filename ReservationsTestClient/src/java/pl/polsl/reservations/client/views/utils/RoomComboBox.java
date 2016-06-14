package pl.polsl.reservations.client.views.utils;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RoomComboBox extends JPanel {

    private static final long serialVersionUID = 4605613990898981677L;

    private final JComboBox mainComboBox;
    private final JComboBox subComboBox;
    private final Hashtable subItems = new Hashtable();
    private String previouslySelectedRoom;

    
    public RoomComboBox(){
        mainComboBox = new JComboBox();
        add(mainComboBox, BorderLayout.WEST);
        subComboBox = new JComboBox();
        subComboBox.setPrototypeDisplayValue("XXXXXXXXXX");
        add(subComboBox, BorderLayout.EAST);
        previouslySelectedRoom = null;
    }

    public Boolean onAction() {
        String item = (String) mainComboBox.getSelectedItem();
        Object o = subItems.get(item);
        previouslySelectedRoom = (String) subComboBox.getSelectedItem();
        if (o == null) {
            subComboBox.setModel(new DefaultComboBoxModel());
        } else if (o instanceof Object[]) {
            subComboBox.setModel(new DefaultComboBoxModel((Object[]) o));
            for (Object element : (Object[]) o) {
                if (element instanceof String && element.equals(previouslySelectedRoom)) {
                    subComboBox.setSelectedItem(element);
                }
            }
            return true;
        }
        return false;

    }

    public void addActionListener(ActionListener l) {
        mainComboBox.addActionListener(l);
        subComboBox.addActionListener(l);
    }
    
    public void addItemListener(ItemListener l){
        mainComboBox.addItemListener(l);
        subComboBox.addItemListener(l);
    }

    public void setFloors(List<Integer> floors) {
        mainComboBox.removeAllItems();
        List<String> floorStringList = new ArrayList<>();
        floors.forEach((floor) -> floorStringList.add(floor.toString()));
        for (String floor : floorStringList) {
            mainComboBox.addItem(floor);
        }

    }

    public void setRooms(List<Integer> roomNumberList, Integer floor) {
        subComboBox.removeAllItems();
        List<String> roomNumberListString = new ArrayList<>();
        roomNumberList.forEach((roomNumber) -> roomNumberListString.add(roomNumber.toString()));
        subItems.put(floor.toString(), roomNumberListString.toArray());
    }

    public void selectItem(Integer floor, Integer roomNumber) {
        if (floor == null && roomNumber == null) {
            return;
        }
        mainComboBox.setSelectedItem(floor.toString());
        Object selectedItem = subItems.get(floor.toString());
        if (selectedItem instanceof Object[]) {
            Object[] selectedStrings = (Object[]) selectedItem;
            for (Object item : selectedStrings) {
                String stringItem = (String)item;
                if (stringItem.equals(roomNumber.toString())) {
                    subComboBox.setModel(new DefaultComboBoxModel(selectedStrings));
                    subComboBox.setSelectedItem(stringItem);
                }
            }
        }
    }

    public Integer getSelectedItem() {
        String selectedFloor = (String) mainComboBox.getSelectedItem();
        if(selectedFloor == null){
            return null;
        }
        Integer selectedFloorNumber = Integer.parseInt(selectedFloor);
        String selectedRoom = (String) subComboBox.getSelectedItem();
        if(selectedRoom == null){
            return null;
        }
        Integer selectedRoomNumber = Integer.parseInt(selectedRoom);
        return (selectedFloorNumber * 100 + selectedRoomNumber);
    }

}
