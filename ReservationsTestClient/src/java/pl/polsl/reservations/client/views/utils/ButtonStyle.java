/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views.utils;

import java.awt.Component;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author abienioszek
 */
public class ButtonStyle {
    
    public static void setStyle(JButton button, Image img) {
        button.setIcon(new ImageIcon(img.getScaledInstance(-1 , 40,
                java.awt.Image.SCALE_SMOOTH)));
        button.setMargin(new Insets(0,5,0,5));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorderPainted(false);
    }

  
    
}
