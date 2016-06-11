package pl.polsl.reservations.client.views.utils;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abienioszek
 */
public class DayTableModel extends DefaultTableModel {

    String[] days = new String[]{"0:00 - 8:00", "8:00 - 16:00", "16:00 - 0:00"};

    private static final long serialVersionUID = 6242787928034473747L;

    public DayTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
        super.setColumnIdentifiers(days);
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return 32;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
