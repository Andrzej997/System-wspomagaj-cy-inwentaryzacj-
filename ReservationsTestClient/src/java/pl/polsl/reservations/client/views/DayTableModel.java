package pl.polsl.reservations.client.views;

import javax.swing.table.AbstractTableModel;

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
