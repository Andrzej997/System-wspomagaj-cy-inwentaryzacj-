package pl.polsl.reservations.client.views.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class CustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 224869581883258761L;
    
    private static final int SPECIAL_ROW = 1;
    private static final int SPECIAL_COULMN = 1;

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Only for specific cell
        if (row == SPECIAL_ROW && column == SPECIAL_COULMN) {
            c.setFont(Font.getFont("ArialBlack"));
            // you may want to address isSelected here too
            c.setForeground(Color.BLACK);
            c.setBackground(Color.MAGENTA);
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}
