package pl.polsl.reservations.client.views;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author abienioszek
 */
public class DayTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 6242787928034473747L;

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
