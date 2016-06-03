/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views.utils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RoomComboBox extends JPanel implements ActionListener {

    private final JComboBox mainComboBox;
    private final JComboBox subComboBox;
    private final Hashtable subItems = new Hashtable();
    private String previouslySelectedRoom;

    public RoomComboBox() {
        mainComboBox = new JComboBox();
        mainComboBox.addActionListener(this);
        add(mainComboBox, BorderLayout.WEST);
        subComboBox = new JComboBox();
        subComboBox.setPrototypeDisplayValue("XXXXXXXXXX");
        add(subComboBox, BorderLayout.EAST);
        previouslySelectedRoom = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String item = (String) mainComboBox.getSelectedItem();
        Object o = subItems.get(item);
        previouslySelectedRoom = (String) subComboBox.getSelectedItem();
        if (o == null) {
            subComboBox.setModel(new DefaultComboBoxModel());
        } else if(o instanceof Object[]) {
            subComboBox.setModel(new DefaultComboBoxModel((Object[]) o));
            for(Object element : (Object[]) o){
                if(element instanceof String && element.equals(previouslySelectedRoom))
                    subComboBox.setSelectedItem(element);
            }
        }
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
        if (selectedItem instanceof String[]) {
            String[] selectedStrings = (String[]) selectedItem;
            for (String item : selectedStrings) {
                if (item.equals(roomNumber.toString())) {
                    subComboBox.setModel(new DefaultComboBoxModel(selectedStrings));
                    subComboBox.setSelectedItem(item);
                }
            }
        }
    }

}
