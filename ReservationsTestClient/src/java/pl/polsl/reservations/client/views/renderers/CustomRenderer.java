package pl.polsl.reservations.client.views.renderers;

import java.util.HashMap;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class CustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 224869581883258761L;

    private HashMap<Color, List<Integer>> colorMap;

    private static final String[] HOURS = new String[]{"8:00", "9:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00"};

    public CustomRenderer() {

    }

    public CustomRenderer(HashMap<Color, List<Integer>> colorMap) {
        this.colorMap = colorMap;
    }

    private Boolean paintCell(Integer row, Integer column, Component c) {
  //      Border b;
        
   //      b = BorderFactory.createCompoundBorder();
        
        for (Entry<Color, List<Integer>> cellsColor : colorMap.entrySet()) {
            List<Integer> colorableCellsAddresses = cellsColor.getValue();
            for (Integer colorableCellAddress : colorableCellsAddresses) {
                Integer colorableRow = colorableCellAddress % 32;
                Integer colorableColumn = colorableCellAddress / 32;
                if (row.equals(colorableRow) && colorableColumn.equals(column - 1)) {
                    c.setBackground(cellsColor.getKey());
                    c.setForeground(Color.BLACK);
                    return true;
                }
            }
        }
        return false;
    }

    public Component getHeaderCellComponent(JTable table, Integer row, Integer column, Component c) {
        if (row % 4 == 0) {
            table.setValueAt(HOURS[row / 4], row, column);
        }
        c.setBackground(Color.lightGray);
        c.setForeground(Color.BLACK);
        c.setFont(new Font("ArialBlack", Font.BOLD, 12));
        return c;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column != 0) {
            if (paintCell(row, column, c)) {
                c.setFont(Font.getFont("ArialBlack"));
            } else {
                c.setForeground(Color.BLACK);
                c.setBackground(Color.WHITE);
            }
        } else {
            c = getHeaderCellComponent(table, row, column, c);
        }
        return c;
    }
}
