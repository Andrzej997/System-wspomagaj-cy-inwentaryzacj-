package pl.polsl.reservations.client.views.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import pl.polsl.reservations.client.views.utils.Pair;

/**
 *
 * @author matis
 */
public class WeekCustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 224869581883258761L;

    private HashMap<Color, List<Integer>> colorMap;

    private List<Pair<Integer, Integer>> startQuarters;
    private List<Pair<Integer, Integer>> endQuarters;

    private static final String[] HOURS = new String[]{"8:00", "9:00", "10:00", "11:00",
        "12:00", "13:00", "14:00", "15:00"};

    public WeekCustomRenderer() {

    }

    public WeekCustomRenderer(HashMap<Color, List<Integer>> colorMap, List<Pair<Integer, Integer>> startQuarters, List<Pair<Integer, Integer>> endQuarters) {
        this.colorMap = colorMap;
        this.startQuarters = startQuarters;
        this.endQuarters = endQuarters;
    }

    private Boolean paintCell(Integer row, Integer column, JComponent c) {
        Border b;
        boolean startQuarter = false;
        boolean endQuarter = false;

        for (Pair<Integer, Integer> quarter : startQuarters) {
            if (((quarter.getFirst() + 1) == column) && (quarter.getSecond().equals(row))) {
                startQuarter = true;
                break;
            }
        }

        for (Pair<Integer, Integer> quarter : endQuarters) {
            if (((quarter.getFirst() + 1) == column) && (quarter.getSecond().equals(row))) {
                endQuarter = true;
                break;
            }
        }

        b = BorderFactory.createCompoundBorder();
        if (startQuarter) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        }
        if (endQuarter) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        }
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));

        for (Entry<Color, List<Integer>> cellsColor : colorMap.entrySet()) {
            List<Integer> colorableCellsAddresses = cellsColor.getValue();
            for (Integer colorableCellAddress : colorableCellsAddresses) {
                Integer colorableRow = colorableCellAddress % 32;
                Integer colorableColumn = colorableCellAddress / 32;
                if (row.equals(colorableRow) && colorableColumn.equals(column - 1)) {
                    c.setBackground(cellsColor.getKey());
                    c.setForeground(Color.BLACK);

                    c.setBorder(b);
                    return true;
                }
            }
        }
        return false;
    }

    public JComponent getHeaderCellComponent(JTable table, Integer row, Integer column, JComponent c) {
        if (row % 4 == 0) {
            table.setValueAt(HOURS[row / 4], row, column);
        }
        c.setBackground(Color.lightGray);
        c.setForeground(Color.BLACK);
        c.setFont(new Font("ArialBlack", Font.BOLD, 12));
        return c;
    }

    private void drawTimeBorders(int row, JComponent c) {
        Border b;

        b = BorderFactory.createCompoundBorder();
        if (row%4==0) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        }else{
             b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        }
        if ((row+1)%4==0) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        }else{
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        }
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        c.setBorder(b);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent c = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (column != 0) {
            
            drawTimeBorders(row, c);
            
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
