package pl.polsl.reservations.client.views.utils;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author abienioszek
 */
public class PanelStyle {

    public static void setSize(JComponent panel, int width, int height) {
        panel.setMaximumSize(new java.awt.Dimension(width, height));
        panel.setMinimumSize(new java.awt.Dimension(width, height));
        panel.setPreferredSize(new java.awt.Dimension(width, height));
    }

    public static void setSize(JLabel panel, int width, int height, boolean alignment) {
        panel.setMaximumSize(new java.awt.Dimension(width, height));
        panel.setMinimumSize(new java.awt.Dimension(width, height));
        panel.setPreferredSize(new java.awt.Dimension(width, height));
        if (alignment) {
            panel.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }

    public static void setSize(JComponent panel, int width, int height, boolean alignment) {
        panel.setMaximumSize(new java.awt.Dimension(width, height));
        panel.setMinimumSize(new java.awt.Dimension(width, height));
        panel.setPreferredSize(new java.awt.Dimension(width, height));
        if (alignment) {
            panel.setAlignmentX(SwingConstants.CENTER);
        }
    }

}
