package pl.polsl.reservations.client.views.utils;

import javax.swing.JComponent;

/**
 *
 * @author abienioszek
 */
public class PanelStyle {
    
    public static void setSize(JComponent panel, int width, int height){
        panel.setMaximumSize(new java.awt.Dimension(width, height));
        panel.setMinimumSize(new java.awt.Dimension(width, height));
        panel.setPreferredSize(new java.awt.Dimension(width, height));
    }
    
    
}
