/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
