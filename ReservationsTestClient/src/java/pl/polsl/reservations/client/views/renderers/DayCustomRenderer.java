package pl.polsl.reservations.client.views.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class DayCustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -672615827745599092L;

    private HashMap<Color, List<Integer>> colorMap;

    public DayCustomRenderer() {
    }

    public DayCustomRenderer(HashMap<Color, List<Integer>> colorMap) {
        this.colorMap = colorMap;
    }

    private Boolean paintCell(Integer row, Integer column, Component c) {
        //      Border b;

        //      b = BorderFactory.createCompoundBorder();
        for (Map.Entry<Color, List<Integer>> cellsColor : colorMap.entrySet()) {
            List<Integer> colorableCellsAddresses = cellsColor.getValue();
            for (Integer colorableCellAddress : colorableCellsAddresses) {
                Integer colorableRow = colorableCellAddress % 32;
                Integer colorableColumn = colorableCellAddress / 32;
                if (row.equals(colorableRow) && colorableColumn.equals(column)) {
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

        if (paintCell(row, column, c)) {
            c.setFont(Font.getFont("ArialBlack"));
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        return c;
    }

}
