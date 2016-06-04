package pl.polsl.reservations.client.views.renderers;

import java.util.HashMap;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class CustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 224869581883258761L;

    private HashMap<Color, List<Integer>> colorMap;

    public CustomRenderer() {

    }

    public CustomRenderer(HashMap<Color, List<Integer>> colorMap) {
        this.colorMap = colorMap;
    }

    private Boolean paintCell(JTable table, Integer row, Integer column, Component c) {
        for (Entry<Color, List<Integer>> cellsColor : colorMap.entrySet()) {
            List<Integer> colorableCellsAddresses = cellsColor.getValue();
            Integer columnCount = table.getColumnCount();
            for (Integer colorableCellAddress : colorableCellsAddresses) {
                Integer colorableRow = colorableCellAddress % 32;
                Integer colorableColumn = colorableCellAddress / 32;
                if (row.equals(colorableRow) && column.equals(colorableColumn)) {
                    c.setBackground(cellsColor.getKey());
                    c.setForeground(Color.BLACK);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        
        if (paintCell(table, row, column, c)) {
            c.setFont(Font.getFont("ArialBlack"));
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        return c;
    }
}
