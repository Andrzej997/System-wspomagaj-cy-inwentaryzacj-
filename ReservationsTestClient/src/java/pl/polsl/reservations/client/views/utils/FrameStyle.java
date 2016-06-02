/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.client.views.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pl.polsl.reservations.client.views.AddEditUserView;

/**
 *
 * @author abienioszek
 */
public class FrameStyle {
    
    public static void dialogStyle(JPanel panel, String title){
         JDialog mFrame = new JDialog();
         mFrame.setResizable(false);
            mFrame.setContentPane(panel);
            mFrame.setVisible(true);
            mFrame.pack();
            centreWindow(mFrame);
    }
    
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
    
}
