package pl.polsl.reservations.client.views.renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
class CustomRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column, Color color)
    {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setForeground(color);
        return c;
    }
}
