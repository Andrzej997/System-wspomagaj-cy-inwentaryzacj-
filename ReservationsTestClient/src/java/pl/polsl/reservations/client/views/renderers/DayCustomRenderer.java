package pl.polsl.reservations.client.views.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author matis
 */
public class DayCustomRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = -672615827745599092L;

    private HashMap<Color, List<Integer>> colorMap;

    private List<Integer> startQuarters;
    private List<Integer> endQuarters;

    private Integer chosenStartQuarter;
    private Integer chosenEndQuarter;

    public DayCustomRenderer() {
    }

    public DayCustomRenderer(HashMap<Color, List<Integer>> colorMap,
            List<Integer> startQuarters, List<Integer> endQuarters) {
        this.colorMap = colorMap;
        this.startQuarters = startQuarters;
        this.endQuarters = endQuarters;
        chosenEndQuarter = null;
        chosenStartQuarter = null;
    }

    public DayCustomRenderer(HashMap<Color, List<Integer>> colorMap,
            List<Integer> startQuarters, List<Integer> endQuarters, Integer chosenStartQuarter, Integer chosenEndQuarter) {
        this.colorMap = colorMap;
        this.startQuarters = startQuarters;
        this.endQuarters = endQuarters;
        this.chosenStartQuarter = chosenStartQuarter;
        this.chosenEndQuarter = chosenEndQuarter;
    }

    private Boolean paintCell(Integer row, Integer column, JComponent c) {
        Border b;
        boolean startQuarter = false;
        boolean endQuarter = false;

        boolean paintChosen = false;

        Integer cellQuarter = row + column * 32;

        if (chosenStartQuarter != null && chosenEndQuarter != null) {
            if (cellQuarter >= chosenStartQuarter && cellQuarter <= chosenEndQuarter) {
                paintChosen = true;
            }
        }

        for (Integer quarter : startQuarters) {
            Integer absoluteAddress = row + column * 32;
            if (absoluteAddress.equals(quarter)) {
                startQuarter = true;
                break;
            }
        }

        for (Integer quarter : endQuarters) {
            Integer absoluteAddress = row + column * 32;
            if (absoluteAddress.equals(quarter)) {
                endQuarter = true;
                break;
            }
        }

        Color borderColor = Color.BLACK;

        if (paintChosen) {
            borderColor = Color.YELLOW;
        }

        b = BorderFactory.createCompoundBorder();
        if (startQuarter) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(2, 0, 0, 0, borderColor));
        }
        if (endQuarter) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 2, 0, borderColor));
        }
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 2, 0, 0, borderColor));
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 0, 2, borderColor));

        for (Map.Entry<Color, List<Integer>> cellsColor : colorMap.entrySet()) {
            List<Integer> colorableCellsAddresses = cellsColor.getValue();
            for (Integer colorableCellAddress : colorableCellsAddresses) {
                Integer colorableRow = colorableCellAddress % 32;
                Integer colorableColumn = colorableCellAddress / 32;
                if (row.equals(colorableRow) && colorableColumn.equals(column)) {
                    c.setBackground(cellsColor.getKey());
                    c.setForeground(Color.BLACK);
                    c.setBorder(b);
                    return true;
                }
            }
        }
        return false;
    }

    private void drawTimeBorders(int row, JComponent c) {
        Border b;

        b = BorderFactory.createCompoundBorder();
        if (row % 4 == 0) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        } else {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        }
        if ((row + 1) % 4 == 0) {
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        } else {
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

        drawTimeBorders(row, c);

        if (paintCell(row, column, c)) {
            c.setFont(Font.getFont("ArialBlack"));
        } else {
            c.setForeground(Color.BLACK);
            c.setBackground(Color.WHITE);
        }
        return c;
    }

}
