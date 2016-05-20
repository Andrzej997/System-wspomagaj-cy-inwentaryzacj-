/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.Date;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author abienioszek
 */
public class DayTableModel extends AbstractTableModel {

    //WYPELNIANIE DANYMI!!!
    @Override
            public int getColumnCount() {
                return 3;
            }

            @Override
            public int getRowCount() {
                return 32;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return row;
            }
}
