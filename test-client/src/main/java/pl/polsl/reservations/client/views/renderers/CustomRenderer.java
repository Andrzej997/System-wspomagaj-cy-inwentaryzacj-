package pl.polsl.reservations.client.views.renderers;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class CustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 224869581883258761L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        /*  if(value instanceof String){
            c.setForeground(Color.blue);
            c.setBackground(Color.red);
        }*/
        return c;
    }
}
