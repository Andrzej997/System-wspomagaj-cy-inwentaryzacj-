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
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class RoomComboBox extends JPanel implements ActionListener {

    private final JComboBox mainComboBox;
    private final JComboBox subComboBox;
    private final Hashtable subItems = new Hashtable();

    public RoomComboBox() {
        String[] items = {"Select Floor", "1 floor", "2 floor", "3 floor"};
        mainComboBox = new JComboBox(items);
        mainComboBox.addActionListener(this);
        add(mainComboBox, BorderLayout.WEST);
        subComboBox = new JComboBox();
        subComboBox.setPrototypeDisplayValue("XXXXXXXXXX");
        add(subComboBox, BorderLayout.EAST);
        String[] subItems1 = {"Select Room", "1", "2", "3"};
        subItems.put(items[1], subItems1);
        String[] subItems2 = {"Select Room", "1", "2", "3"};
        subItems.put(items[2], subItems2);
        String[] subItems3 = {"Select Room", "1", "2", "3"};
        subItems.put(items[3], subItems3);
    }

    public void actionPerformed(ActionEvent e) {
        String item = (String) mainComboBox.getSelectedItem();
        Object o = subItems.get(item);

        if (o == null) {
            subComboBox.setModel(new DefaultComboBoxModel());
        } else {
            subComboBox.setModel(new DefaultComboBoxModel((String[]) o));
        }
    }

    public void setFloors(List<String> floors) {
        mainComboBox.removeAllItems();

        mainComboBox.addItem("Select Floor");

        for (String floor : floors) {
            mainComboBox.addItem(floor);
        }

    }

    public void setRooms(List<Integer> rooms,Integer floor) {
       subComboBox.removeAllItems();

        subComboBox.addItem("Select Floor");

        for (String floor : floors) {
            subComboBox.addItem(floor);
        }
    }

    public void setItem(Integer currentItem) {

    }
}
